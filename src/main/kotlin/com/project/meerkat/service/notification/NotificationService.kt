package com.project.meerkat.service.notification

import com.project.meerkat.common.model.UserInfo
import com.project.meerkat.common.util.ContextHolderUtil
import com.project.meerkat.exception.CommonException
import com.project.meerkat.exception.ErrorCode
import com.project.meerkat.model.notification.AddNotificationRequest
import com.project.meerkat.model.notification.ModifyNotificationRequest
import com.project.meerkat.model.notification.NotificationEntity
import com.project.meerkat.repository.notification.NotificationJpaRepository
import org.apache.commons.lang3.ObjectUtils
import org.apache.commons.lang3.StringUtils
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import java.time.LocalDateTime
import java.util.Collections
import javax.transaction.Transactional

@Service
class NotificationService(
    val modelMapper: ModelMapper,
    val notificationJpaRepository: NotificationJpaRepository
) {
    @Transactional
    fun addNotification(addNotificationRequest: AddNotificationRequest) {
        val userInfo = getUserInfoWithCheck()
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
        val userInfo = getUserInfoWithCheck()

        val notificationEntity = notificationJpaRepository.selectNotificationByMemberNo(notificationNo, userInfo.memberNo)
        if (ObjectUtils.isEmpty(notificationEntity)) {
            throw CommonException(ErrorCode.NOT_FOUND, "notification is not exist.", HttpStatus.NOT_FOUND)
        }

        return notificationEntity!!
    }

    fun getNotifications(): List<NotificationEntity>? {
        val userInfo = getUserInfoWithCheck()

        val notificationList = notificationJpaRepository.selectNotificationsByMemberNo(userInfo.memberNo)
        return if (CollectionUtils.isEmpty(notificationList)) Collections.emptyList() else notificationList
    }

    @Transactional
    fun removeNotification(notificationNo: String) {
        val userInfo = getUserInfoWithCheck()

        notificationJpaRepository.deleteNotification(notificationNo, userInfo.memberNo)
    }

    @Transactional
    fun modifyNotification(modifyNotificationRequest: ModifyNotificationRequest) {
        val userInfo = getUserInfoWithCheck()
        val notificationEntity = notificationJpaRepository.selectNotificationByMemberNo(modifyNotificationRequest.notificationNo, userInfo.memberNo)
        if (ObjectUtils.isEmpty(notificationEntity)) {
            throw CommonException(ErrorCode.NOT_FOUND, "notification is not exist.", HttpStatus.NOT_FOUND)
        }

        notificationEntity!!.apply {
            name = modifyNotificationRequest.name
            notiTime = modifyNotificationRequest.notiTime
        }

        notificationJpaRepository.updateNotification(notificationEntity)
    }

    private fun getUserInfoWithCheck(): UserInfo {
        val userInfo = ContextHolderUtil.getUserInfo()
        if (ObjectUtils.isEmpty(userInfo) || StringUtils.isEmpty(userInfo?.memberNo)) {
            throw CommonException(ErrorCode.AUTHENTICATION_REQUIRED, "NotificationService.addNotification() userInfo doesn't exist. check login.", HttpStatus.UNAUTHORIZED)
        }

        return userInfo!!
    }
}