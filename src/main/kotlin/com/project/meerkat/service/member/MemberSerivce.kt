package com.project.meerkat.service.member

import com.project.meerkat.common.model.UserInfo
import com.project.meerkat.common.service.JwtService
import com.project.meerkat.common.util.ContextHolderUtil
import com.project.meerkat.constant.member.MemberStatusCode
import com.project.meerkat.constant.member.MemberTypeCode
import com.project.meerkat.exception.CommonException
import com.project.meerkat.exception.ErrorCode
import com.project.meerkat.model.member.AddMemberRequest
import com.project.meerkat.model.member.LoginMemberRequest
import com.project.meerkat.model.member.Member
import com.project.meerkat.model.member.entity.MemberEntity
import com.project.meerkat.repository.member.MemberJpaRepository
import com.project.meerkat.repository.member.MemberRepository
import com.project.meerkat.service.gmail.MailManager
import org.apache.commons.lang3.ObjectUtils
import org.apache.commons.lang3.StringUtils
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class MemberSerivce(
    private val memberRepository: MemberRepository,
    private val modelMapper: ModelMapper,
    private val jwtService: JwtService,
    private val memberJpaRepository: MemberJpaRepository,
    private val mailManager: MailManager
) {
    fun isExistMember(email: String): Boolean {
        return ObjectUtils.isNotEmpty(memberJpaRepository.findMemberByEmail(email))
    }

    fun getMemberByEmail(email: String): MemberEntity? {
        return memberJpaRepository.findMemberByEmail(email)
    }

    fun addMember(addMemberRequest: AddMemberRequest) {
        if (isExistMember(addMemberRequest.email)) {
            throw IllegalArgumentException("already exist user")
        }

        val member = modelMapper.map(addMemberRequest, Member::class.java)
        member.statusCode = MemberStatusCode.ACTIVE
        member.typeCode = MemberTypeCode.USER

        memberRepository.insertMember(member)
    }

    @Transactional
    fun login(loginMemberRequest: LoginMemberRequest): UserInfo {
        var memberEntity: MemberEntity? = memberJpaRepository.findMemberByEmail(loginMemberRequest.email)

        if (ObjectUtils.isEmpty(memberEntity)) {
            memberEntity = MemberEntity(
                loginMemberRequest.email,
                loginMemberRequest.name,
                MemberStatusCode.ACTIVE,
                MemberTypeCode.USER,
                loginMemberRequest.imgPath
            )

            memberJpaRepository.insertMember(memberEntity)
        } else {
            memberEntity!!.apply {
                lastLoginTime = LocalDateTime.now()
                modTime = LocalDateTime.now()
            }
        }

        return this.makeUserInfoWithJwtResponse(memberEntity)
    }

    @Transactional
    fun updateMemberNotificationEmail(notificationEmail: String): UserInfo {
        if (StringUtils.isEmpty(notificationEmail)) {
            throw CommonException(ErrorCode.BAD_REQUEST, "MemberService.updateMemberNotificationEmail(), notification email is empty.", HttpStatus.BAD_REQUEST)
        }

        val userInfo = ContextHolderUtil.getUserInfoWithCheck()
        val memberEntity = modelMapper.map(userInfo, MemberEntity::class.java)
        memberEntity!!.apply {
            this.notificationEmail = notificationEmail
            modTime = LocalDateTime.now()
        }

        return this.makeUserInfoWithJwtResponse(memberEntity)
    }

    fun sendAddressCheckMail(email: String) {
        mailManager.send(email, "이메일 주소 확인 - Meerkat", getAddressCheckEmailContentTemplate())
    }

    private fun getAddressCheckEmailContentTemplate(): String {
        return """
            <div style='text-align: center;'>
            <h1>Meerkat</h1>
            <h2>이메일 주소가 확인 되었습니다.</h2>
            <p>작성 중이시던 페이지로 돌아가 등록을 이어 주세요.</p>
            </div>
        """.trimIndent()
    }

    private fun makeUserInfoWithJwtResponse(memberEntity: MemberEntity): UserInfo {
        val userInfo = modelMapper.map(memberEntity, UserInfo::class.java)
        userInfo.jwtToken = jwtService.createJwt(userInfo.email, userInfo.memberNo)

        return userInfo
    }
}
