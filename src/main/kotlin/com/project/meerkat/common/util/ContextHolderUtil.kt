package com.project.meerkat.common.util

import com.project.meerkat.common.model.UserInfo
import com.project.meerkat.exception.CommonException
import com.project.meerkat.exception.ErrorCode
import org.apache.commons.lang3.ObjectUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.http.HttpStatus

class ContextHolderUtil {
    companion object {
        private var userInfoThreadLocal = ThreadLocal<UserInfo>()

        private fun getUserInfo(): UserInfo? {
            return userInfoThreadLocal.get()
        }

        fun putUserInfo(userInfo: UserInfo) {
            userInfoThreadLocal.set(userInfo)
        }

        fun clearUserInfo() {
            userInfoThreadLocal.remove()
        }

        fun getUserInfoWithCheck(): UserInfo {
            val userInfo = getUserInfo()
            if (ObjectUtils.isEmpty(userInfo) || StringUtils.isEmpty(userInfo?.memberNo)) {
                throw CommonException(ErrorCode.AUTHENTICATION_REQUIRED, "NotificationService.addNotification() userInfo doesn't exist. check login.", HttpStatus.UNAUTHORIZED)
            }

            return userInfo!!
        }
    }
}