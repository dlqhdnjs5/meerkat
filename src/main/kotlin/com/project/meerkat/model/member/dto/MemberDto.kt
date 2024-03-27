package com.project.meerkat.model.member.dto

import com.project.meerkat.constant.member.MemberStatusCode
import com.project.meerkat.constant.member.MemberTypeCode
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

