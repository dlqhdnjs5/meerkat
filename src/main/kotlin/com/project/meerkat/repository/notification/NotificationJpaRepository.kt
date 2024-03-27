package com.project.meerkat.repository.notification

import com.project.meerkat.model.notification.NotificationEntity
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class NotificationJpaRepository(
    @PersistenceContext private val entityManager: EntityManager
) {
    fun insertNotification(notificationEntity: NotificationEntity) {
        entityManager.persist(notificationEntity)
    }

}