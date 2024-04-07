package com.project.meerkat.controller.notification

import com.project.meerkat.model.notification.AddNotificationRequest
import com.project.meerkat.model.notification.ModifyNotificationRequest
import com.project.meerkat.model.notification.NotificationEntity
import com.project.meerkat.service.notification.NotificationService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/notifications")
class NotificationController(
    val notificationService: NotificationService
) {
    @PostMapping
    fun addNotification(@RequestBody @Valid addNotificationRequest: AddNotificationRequest) {
        notificationService.addNotification(addNotificationRequest)
    }

    @PutMapping
    fun modifyNotification(@RequestBody @Valid modifyNotificationRequest: ModifyNotificationRequest) {
        notificationService.modifyNotification(modifyNotificationRequest)
    }

    @GetMapping
    fun getNotifications(): List<NotificationEntity>? {
        return notificationService.getNotifications()
    }

    @GetMapping("/{notificationNo}")
    fun getNotification(@PathVariable("notificationNo") notificationNo: String): NotificationEntity {
        return notificationService.getNotification(notificationNo)
    }

    @DeleteMapping("/{notificationNo}")
    fun getNotifications(@PathVariable("notificationNo") notificationNo: String) {
        notificationService.removeNotification(notificationNo)
    }
}