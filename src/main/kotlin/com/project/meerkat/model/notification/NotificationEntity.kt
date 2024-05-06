package com.project.meerkat.model.notification

import com.fasterxml.jackson.annotation.JsonIgnore
import com.project.meerkat.model.member.entity.MemberEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "notification")
data class NotificationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_no", nullable = false)
    val notificationNo: String? = null,

    @Column(name = "member_no")
    var memberNo: String? = null,

    @Column(name = "name", nullable = false, length = 100)
    var name: String,

    @Column(name = "bname", nullable = false, length = 100)
    val bname: String,

    @Column(name = "bcode", nullable = false)
    val bcode: String,

    @Column(name = "post_no", nullable = false)
    val postNo: String,

    @Column(name = "sigungu_code", nullable = false)
    val sigunguCode: String,

    @Column(name = "user_address", nullable = false, length = 300)
    val userAddress: String,

    @Column(name = "noti_time", nullable = false)
    var notiTime: String,

    @Column(name = "enable", nullable = false)
    var enable: Boolean,

    @Column(name = "reg_time", nullable = false)
    var regTime: LocalDateTime,

    @Column(name = "mod_time", nullable = false)
    var modTime: LocalDateTime,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", referencedColumnName = "member_no", insertable = false, updatable = false)
    var memberEntity: MemberEntity? = null
)
