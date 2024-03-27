package com.project.meerkat.controller.notification

import com.project.meerkat.model.notification.AddNotificationRequest
import com.project.meerkat.service.notification.NotificationService
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Slf4j
@RequestMapping("/notifications")
class NotificationController(
    val notificationService: NotificationService
) {
    @PostMapping
    fun addNotification(@RequestBody addNotificationRequest: AddNotificationRequest) {
        notificationService.addNotification(addNotificationRequest)
    }
}