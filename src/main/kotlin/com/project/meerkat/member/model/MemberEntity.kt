package com.project.meerkat.member.model

import com.project.meerkat.member.constant.MemberStatusCode
import com.project.meerkat.member.constant.MemberTypeCode
import org.apache.ibatis.type.Alias
import java.time.LocalDateTime

@Alias("MemberEntity")
data class MemberEntity(
    var memberNo: String?,
    var email: String,
    var name: String,
    var imgPath: String,
    var typeCode: MemberTypeCode?,
    var statusCode: MemberStatusCode?,
    var regTime: LocalDateTime?,
    var lastLoginTime: LocalDateTime?,
) {
    constructor(): this(memberNo="", email = "", name = "", imgPath = "", typeCode = null, statusCode = null, regTime = null, lastLoginTime = null)
}
