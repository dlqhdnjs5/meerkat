package com.project.meerkat.model.notification

import javax.validation.constraints.NotEmpty

data class AddNotificationRequest(
    @get:NotEmpty
    val bcode: String,
    @get:NotEmpty
    val bname: String,
    @get:NotEmpty
    val name: String,
    @get:NotEmpty
    val notiTime: String,
    @get:NotEmpty
    val postNo: String,
    @get:NotEmpty
    val sigunguCode: String,
    @get:NotEmpty
    val userAddress: String
)
