package com.project.meerkat.member.service

import com.project.meerkat.common.model.UserInfo
import com.project.meerkat.common.service.JwtService
import com.project.meerkat.member.constant.MemberStatusCode
import com.project.meerkat.member.constant.MemberTypeCode
import com.project.meerkat.member.model.AddMemberRequest
import com.project.meerkat.member.model.LoginMemberRequest
import com.project.meerkat.member.model.MemberEntity
import com.project.meerkat.member.repository.MemberRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberSerivce(
    private val memberRepository: MemberRepository,
    private val modelMapper: ModelMapper,
    private val jwtService: JwtService
) {
    fun isExistMember(email: String): Boolean {
        return memberRepository.isExistMemberCount(email) > 0
    }

    fun getMemberByEmail(email: String): MemberEntity {
        return memberRepository.selectMemberByEmail(email)
    }

    fun addMember(addMemberRequest: AddMemberRequest) {
        if (isExistMember(addMemberRequest.email)) {
            throw IllegalArgumentException("already exist user")
        }

        val memberEntity = modelMapper.map(addMemberRequest, MemberEntity::class.java)
        memberEntity.statusCode = MemberStatusCode.ACTIVE
        memberEntity.typeCode = MemberTypeCode.USER

        memberRepository.insertMember(memberEntity)
    }

    @Transactional
    fun login(loginMemberRequest: LoginMemberRequest): UserInfo {
        var memberEntity = modelMapper.map(loginMemberRequest, MemberEntity::class.java)

        if (!isExistMember(loginMemberRequest.email)) {
            memberEntity.typeCode = MemberTypeCode.USER
            memberEntity.statusCode = MemberStatusCode.ACTIVE

            memberRepository.insertMemberAndGenKey(memberEntity)
        } else {
            memberEntity = memberRepository.selectActiveMemberByEmail(loginMemberRequest.email)
        }

        this.updateLastLoginTime(memberEntity)

        return this.makeUserInfoWithJwtResponse(memberEntity)
    }

    private fun updateLastLoginTime(memberEntity: MemberEntity) {
        memberRepository.updateLastLoginTime(memberEntity)
    }

    private fun makeUserInfoWithJwtResponse(memberEntity: MemberEntity): UserInfo {
        val userInfo = modelMapper.map(memberEntity, UserInfo::class.java)
        userInfo.jwtToken = jwtService.createJwt(userInfo.email, userInfo.memberNo)

        return userInfo
    }
}
