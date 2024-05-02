package com.project.meerkat.model.member.entity

import com.project.meerkat.constant.member.MemberStatusCode
import com.project.meerkat.constant.member.MemberTypeCode
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "member")
data class MemberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no")
    val memberNo: String? = null,

    @Column(nullable = false, length = 200)
    var email: String?,

    @Column(name = "notification_email", nullable = true)
    var notificationEmail: String?,

    @Column(nullable = false, length = 100)
    var name: String?,

    @Column(name = "reg_time", nullable = false)
    var regTime: LocalDateTime?,

    @Column(name = "mod_time", nullable = false)
    var modTime: LocalDateTime?,

    @Column(name = "status_code", nullable = false, length = 10)
    var statusCode: String?,

    @Column(name = "last_login_time")
    var lastLoginTime: LocalDateTime? = null,

    @Column(name = "type_code", nullable = false, length = 20)
    var typeCode: String?,

    @Column(name = "img_path")
    var imgPath: String? = null,
) {
    constructor(): this(memberNo="", email = "", notificationEmail = "", name = "", imgPath = "", typeCode = null, statusCode = null, regTime = null, lastLoginTime = null, modTime = null)
    constructor(email: String, name: String, statusCode: MemberStatusCode, typeCode: MemberTypeCode, imgPath: String) : this(memberNo="", email = email, notificationEmail = "", name = name, imgPath = imgPath, typeCode = typeCode.name, statusCode = statusCode.name, regTime = LocalDateTime.now(), lastLoginTime = LocalDateTime.now(), modTime = LocalDateTime.now())
}