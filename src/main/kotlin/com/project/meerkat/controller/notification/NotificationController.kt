package com.project.meerkat.controller.notification

import com.project.meerkat.model.notification.AddNotificationRequest
import com.project.meerkat.model.notification.NotificationEntity
import com.project.meerkat.service.notification.NotificationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notifications")
class NotificationController(
    val notificationService: NotificationService
) {
    @PostMapping
    fun addNotification(@RequestBody addNotificationRequest: AddNotificationRequest) {
        notificationService.addNotification(addNotificationRequest)
    }

    @GetMapping
    fun getNotifications(): MutableList<NotificationEntity>? {
        return notificationService.getNotifications()
    }
}