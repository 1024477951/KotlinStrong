package com.strong.baselib

import com.strong.baselib.bean.UserBean

/**
 * @author liuzhen
 * @date 2023/4/25
 */
object UserInfoUtil {

    private var user: UserBean? = null

    fun getUser(): UserBean? {
        return user
    }

    fun getNickName(): String {
        return if (user?.nickname?.isNotEmpty() == true) user?.nickname ?: ""
        else if (user?.name?.isNotEmpty() == true) user?.name ?: ""
        else ""
    }

}