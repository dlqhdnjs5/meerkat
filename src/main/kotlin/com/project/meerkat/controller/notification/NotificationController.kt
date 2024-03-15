package com.project.meerkat.controller.notification

import com.project.meerkat.model.notification.AddNotificationRequest
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Slf4j
@RequestMapping("/notifications")
class NotificationController {
    @PostMapping
    fun addNotification(@RequestBody addNotificationRequest: AddNotificationRequest): AddNotificationRequest {

        return addNotificationRequest
    }
}