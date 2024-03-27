package com.project.meerkat.model.notification

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "notification")
data class NotificationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_no", nullable = false)
    val notificationNo: Long = 0,

    @Column(name = "name", nullable = false, length = 100)
    val name: String,

    @Column(name = "bname", nullable = false, length = 100)
    val bname: String,

    @Column(name = "bcode", nullable = false)
    val bcode: Long,

    @Column(name = "post_no", nullable = false)
    val postNo: Long,

    @Column(name = "sigungu_code", nullable = false)
    val sigunguCode: Long,

    @Column(name = "user_address", nullable = false, length = 300)
    val userAddress: String,

    @Column(name = "noti_time", nullable = false)
    var notiTime: String,

    @Column(name = "enable", nullable = false)
    var enable: Boolean,

    @Column(name = "reg_time", nullable = false)
    var regTime: LocalDateTime,

    @Column(name = "mod_time", nullable = false)
    var modTime: LocalDateTime
)
