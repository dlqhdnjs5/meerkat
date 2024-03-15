package com.project.meerkat.member.model

import com.project.meerkat.member.constant.MemberStatusCode
import com.project.meerkat.member.constant.MemberTypeCode
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

data class AddMemberRequest(
    var memberNo: String,
    @NotBlank
    var email: String,
    @NotBlank
    var name: String,
    var imgPath: String,
    var typeCode: MemberTypeCode,
    var statusCode: MemberStatusCode,
    var regTime: LocalDateTime,
    var lastLoginTime: LocalDateTime,
)