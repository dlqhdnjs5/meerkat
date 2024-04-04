package com.project.meerkat.repository.notification

import com.project.meerkat.model.notification.NotificationEntity
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery

@Repository
class NotificationJpaRepository(
    @PersistenceContext private val entityManager: EntityManager
) {
    fun insertNotification(notificationEntity: NotificationEntity) {
        entityManager.persist(notificationEntity)
    }

    fun selectNotificationsByMemberNo(memberNo: String): MutableList<NotificationEntity>? {
        val jpql = "SELECT n FROM NotificationEntity n WHERE n.memberNo = :memberNo"
        val query: TypedQuery<NotificationEntity> = entityManager.createQuery(jpql, NotificationEntity::class.java)
        query.setParameter("memberNo", memberNo)
        return query.resultList
    }

    fun deleteNotification(notificationNo: String) {
        val notificationEntity = entityManager.find(NotificationEntity::class.java, notificationNo)
        entityManager.remove(notificationEntity)
    }


}