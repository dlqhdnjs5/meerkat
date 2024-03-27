package com.project.meerkat.service.notification

import com.project.meerkat.model.notification.AddNotificationRequest
import com.project.meerkat.model.notification.NotificationEntity
import com.project.meerkat.repository.notification.NotificationJpaRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class NotificationService(
    val modelMapper: ModelMapper,
    val notificationJpaRepository: NotificationJpaRepository
) {
    @Transactional
    fun addNotification(addNotificationRequest: AddNotificationRequest) {
        val notificationEntity = modelMapper.map(addNotificationRequest, NotificationEntity::class.java)
        val now = LocalDateTime.now()
        notificationEntity.apply {
            enable = true
            regTime = now
            modTime = now
        }

        notificationJpaRepository.insertNotification(notificationEntity)
    }
}