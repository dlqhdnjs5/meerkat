package com.project.meerkat.repository.member

import com.project.meerkat.model.member.Member
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface MemberRepository {

    fun selectMemberByEmail(@Param("email") email: String): Member

    fun insertMember(@Param("memberEntity") member: Member)
}