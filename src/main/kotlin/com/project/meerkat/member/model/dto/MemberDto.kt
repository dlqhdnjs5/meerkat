package com.project.meerkat.member.model.dto

import com.project.meerkat.member.constant.MemberStatusCode
import com.project.meerkat.member.constant.MemberTypeCode
import java.time.LocalDateTime

data class MemberDto(
    var memberNo: String,
    var email: String,
    var name: String,
    var imgPath: String,
    var typeCode: MemberTypeCode,
    var statusCode: MemberStatusCode,
    var regTime: LocalDateTime,
    var lastLoginTime: LocalDateTime,
)

