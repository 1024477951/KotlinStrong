package com.strong.baselib.bean

/**
 * 用户信息
 */
data class UserBean(
    var mobile: String? = null,
    var avatar: String? = null,
    var birthday: String? = null,
    var name: String? = null,
    var nickname: String? = null
) {

    fun getPwdMobile(): String {
        val mobileSize = mobile?.length ?: 0
        val result: String = if (mobile != null && mobileSize > 7) {
            mobile?.substring(0, 3) + "****" + mobile?.substring(
                mobileSize - 4,
                mobileSize
            )
        } else {
            mobile ?: ""
        }
        return result
    }

}