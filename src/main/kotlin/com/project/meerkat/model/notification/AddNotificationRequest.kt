package com.project.meerkat.model.notification

data class AddNotificationRequest(
    val bcode: String,
    val bname: String,
    val name: String,
    val notiTime: String,
    val postNo: String,
    val sigunguCode: String,
    val userAddress: String
)
