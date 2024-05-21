package com.project.meerkat.repository.notification

import com.project.meerkat.exception.CommonException
import com.project.meerkat.exception.ErrorCode
import com.project.meerkat.model.notification.NotificationEntity
import org.apache.commons.lang3.ObjectUtils
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
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

    fun selectNotificationByNotificationNoAndMemberNo(notificationNo: String, memberNo: String): NotificationEntity? {
        val jpql = "SELECT n FROM NotificationEntity n WHERE n.notificationNo = :notificationNo AND n.memberNo = :memberNo"
        val query: TypedQuery<NotificationEntity> = entityManager.createQuery(jpql, NotificationEntity::class.java)
        query.setParameter("notificationNo", notificationNo)
        query.setParameter("memberNo", memberNo)
        return query.singleResult
    }

    fun deleteNotification(notificationNo: String, memberNo: String) {
        val jpql = "SELECT n FROM NotificationEntity n WHERE n.notificationNo = :notificationNo AND n.memberNo = :memberNo"
        val query: TypedQuery<NotificationEntity> = entityManager.createQuery(jpql, NotificationEntity::class.java)
        query.setParameter("notificationNo", notificationNo)
        query.setParameter("memberNo", memberNo)
        val notificationEntity = query.resultList.firstOrNull()

        if (ObjectUtils.isEmpty(notificationEntity)) {
            throw CommonException(ErrorCode.BAD_REQUEST, "There is no notification. notificationNo: ${notificationNo}, memberNo: ${memberNo}", HttpStatus.BAD_REQUEST)
        }

        entityManager.remove(notificationEntity)
    }

    fun selectSigunguCodesForBatch(): List<String> {
        val query: TypedQuery<String> = entityManager.createQuery(
            """
                SELECT DISTINCT n.sigunguCode 
                FROM NotificationEntity n 
                INNER JOIN n.memberEntity m 
                WHERE m.statusCode = 'ACTIVE' AND m.notificationEmail IS NOT NULL
                AND n.enable = TRUE
            """.trimIndent(), String::class.java
        )
        return query.resultList
    }
}