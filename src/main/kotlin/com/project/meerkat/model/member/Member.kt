package com.project.meerkat.model.member

import com.project.meerkat.constant.member.MemberStatusCode
import com.project.meerkat.constant.member.MemberTypeCode
import org.apache.ibatis.type.Alias
import java.time.LocalDateTime

@Alias("Member")
data class Member(
    var memberNo: String?,
    var email: String,
    var notificationEmail: String,
    var name: String,
    var imgPath: String,
    var typeCode: MemberTypeCode?,
    var statusCode: MemberStatusCode?,
    var regTime: LocalDateTime?,
    var lastLoginTime: LocalDateTime?,
) {
    constructor(): this(memberNo="", email = "", notificationEmail= "", name = "", imgPath = "", typeCode = null, statusCode = null, regTime = null, lastLoginTime = null)
}
