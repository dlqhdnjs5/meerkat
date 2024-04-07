package com.project.meerkat.model.notification

import javax.validation.constraints.NotEmpty

data class ModifyNotificationRequest(
    @get:NotEmpty
    val notificationNo: String,
    @get:NotEmpty
    val name: String,
    @get:NotEmpty
    val notiTime: String,
)