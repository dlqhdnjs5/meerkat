package com.project.meerkat.common.util

import com.project.meerkat.common.model.UserInfo

class ContextHolderUtil {
    companion object {
        var userInfoThreadLocal = ThreadLocal<UserInfo>()

        fun getUserInfo(): UserInfo? {
            return userInfoThreadLocal.get()
        }

        fun putUserInfo(userInfo: UserInfo) {
            userInfoThreadLocal.set(userInfo)
        }

        fun clearUserInfo() {
            userInfoThreadLocal.remove()
        }
    }
}