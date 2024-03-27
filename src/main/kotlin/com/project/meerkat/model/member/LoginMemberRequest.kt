package com.project.meerkat.model.member

import com.project.meerkat.constant.member.MemberStatusCode
import com.project.meerkat.constant.member.MemberTypeCode
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

data class LoginMemberRequest(
    var memberNo: String?,
    @NotBlank
    var email: String,
    @NotBlank
    var name: String,
    var imgPath: String,
    var typeCode: MemberTypeCode?,
    var statusCode: MemberStatusCode?,
    var regTime: LocalDateTime?,
    var lastLoginTime: LocalDateTime?,
) {
    constructor(): this(memberNo="", email = "", name = "", imgPath = "", typeCode = MemberTypeCode.USER, statusCode = MemberStatusCode.ACTIVE, regTime = null, lastLoginTime = null)
}
