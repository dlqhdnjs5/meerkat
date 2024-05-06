package com.project.meerkat.service.notification

import com.project.meerkat.common.util.ContextHolderUtil
import com.project.meerkat.exception.CommonException
import com.project.meerkat.exception.ErrorCode
import com.project.meerkat.model.notification.AddNotificationRequest
import com.project.meerkat.model.notification.ModifyNotificationRequest
import com.project.meerkat.model.notification.NotificationEntity
import com.project.meerkat.repository.notification.NotificationJpaRepository
import org.apache.commons.lang3.ObjectUtils
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class NotificationService(
    val modelMapper: ModelMapper,
    val notificationJpaRepository: NotificationJpaRepository
) {
    @Transactional
    fun addNotification(addNotificationRequest: AddNotificationRequest) {
        val userInfo = ContextHolderUtil.getUserInfoWithCheck()
        val notificationEntity = modelMapper.map(addNotificationRequest, NotificationEntity::class.java)

        val now = LocalDateTime.now()
        notificationEntity.apply {
            enable = true
            regTime = now
            modTime = now
            memberNo = userInfo.memberNo
        }

        val notificationList = notificationJpaRepository.selectNotificationsByMemberNo(userInfo.memberNo)
        if (!CollectionUtils.isEmpty(notificationList)) {
            if (notificationList!!.size >= 5) {
                throw CommonException(ErrorCode.BAD_REQUEST, "Try to add notification count over 5.", HttpStatus.UNAUTHORIZED)
            }
        }

        notificationJpaRepository.insertNotification(notificationEntity)
    }

    fun getNotification(notificationNo: String): NotificationEntity {
        val userInfo = ContextHolderUtil.getUserInfoWithCheck()

        val notificationEntity = notificationJpaRepository.selectNotificationByMemberNo(notificationNo, userInfo.memberNo)
        if (ObjectUtils.isEmpty(notificationEntity)) {
            throw CommonException(ErrorCode.NOT_FOUND, "notification is not exist.", HttpStatus.NOT_FOUND)
        }

        return notificationEntity!!
    }

    fun getNotifications(): List<NotificationEntity>? {
        val userInfo = ContextHolderUtil.getUserInfoWithCheck()
        val notificationList = notificationJpaRepository.selectNotificationsByMemberNo(userInfo.memberNo)

        return if (CollectionUtils.isEmpty(notificationList)) emptyList() else notificationList
    }

    @Transactional
    fun removeNotification(notificationNo: String) {
        val userInfo = ContextHolderUtil.getUserInfoWithCheck()

        notificationJpaRepository.deleteNotification(notificationNo, userInfo.memberNo)
    }

    @Transactional
    fun modifyNotification(modifyNotificationRequest: ModifyNotificationRequest) {
        val userInfo = ContextHolderUtil.getUserInfoWithCheck()
        val notificationEntity = notificationJpaRepository.selectNotificationByMemberNo(modifyNotificationRequest.notificationNo, userInfo.memberNo)
        if (ObjectUtils.isEmpty(notificationEntity)) {
            throw CommonException(ErrorCode.NOT_FOUND, "notification is not exist.", HttpStatus.NOT_FOUND)
        }

        notificationEntity!!.apply {
            name = modifyNotificationRequest.name
            notiTime = modifyNotificationRequest.notiTime
            enable = modifyNotificationRequest.enable
        }

        notificationJpaRepository.updateNotification(notificationEntity)
    }

    fun getAllSigunguCodes(): List<String> {
        return notificationJpaRepository.selectSigunguCodesForBatch()
    }
}