package com.project.meerkat.common.model

import com.project.meerkat.constant.member.MemberStatusCode
import com.project.meerkat.constant.member.MemberTypeCode
import java.time.LocalDateTime

data class UserInfo (
    var memberNo: String,
    var email: String,
    var name: String,
    var imgPath: String,
    var typeCode: MemberTypeCode?,
    var statusCode: MemberStatusCode?,
    var regTime: LocalDateTime?,
    var lastLoginTime: LocalDateTime?,
    var jwtToken: String?,
) : JwtToken(jwtToken) {
    constructor() : this("", "", "", "", MemberTypeCode.USER, MemberStatusCode.ACTIVE, null, null, null)
}

