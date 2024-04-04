package com.project.meerkat.service.notification

import com.project.meerkat.common.util.ContextHolderUtil
import com.project.meerkat.exception.CommonException
import com.project.meerkat.exception.ErrorCode
import com.project.meerkat.model.notification.AddNotificationRequest
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
        val notificationEntity = modelMapper.map(addNotificationRequest, NotificationEntity::class.java)
        val userInfo = ContextHolderUtil.getUserInfo()

        if (ObjectUtils.isEmpty(userInfo) || StringUtils.isEmpty(userInfo?.memberNo)) {
            throw IllegalStateException("NotificationService.addNotification() userInfo doesn't exist. check login. addNotificationRequest : $addNotificationRequest")
        }

        val now = LocalDateTime.now()
        notificationEntity.apply {
            enable = true
            regTime = now
            modTime = now
            memberNo = userInfo!!.memberNo
        }

        notificationJpaRepository.insertNotification(notificationEntity)
    }

    fun getNotifications(): List<NotificationEntity>? {
        val userInfo = ContextHolderUtil.getUserInfo()
        if (ObjectUtils.isEmpty(userInfo) || StringUtils.isEmpty(userInfo?.memberNo)) {
            throw CommonException(ErrorCode.AUTHENTICATION_REQUIRED, "NotificationService.addNotification() userInfo doesn't exist. check login.", HttpStatus.UNAUTHORIZED)
        }

        val notificationList = notificationJpaRepository.selectNotificationsByMemberNo(userInfo!!.memberNo)
        return if (CollectionUtils.isEmpty(notificationList)) Collections.emptyList() else notificationList
    }

    @Transactional
    fun removeNotification(notificationNo: String) {
        notificationJpaRepository.deleteNotification(notificationNo)
    }
}