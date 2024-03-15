package com.project.meerkat.member.repository

import com.project.meerkat.member.model.MemberEntity
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface MemberRepository {
    fun isExistMemberCount(@Param("email") email: String): Int

    fun selectMemberByEmail(@Param("email") email: String): MemberEntity

    fun insertMember(@Param("memberEntity") member: MemberEntity)

    fun insertMemberAndGenKey(@Param("memberEntity") member: MemberEntity)

    fun selectActiveMemberByEmail(@Param("email") email: String): MemberEntity

    fun updateLastLoginTime(@Param("memberEntity") member: MemberEntity)
}