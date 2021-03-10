package com.strong.ui.me.bean

/**
 * created by YooJin.
 * date: 2021/1/11 17:28
 * desc:
 */
open class UserBean(){

    constructor(
        mobile: String?,
        avatar: String?,
        birthday: String?,
        name: String?,
        nickname: String?
    ) : this(){
        this.mobile = mobile
        this.avatar = avatar
        this.birthday = birthday
        this.name = name
        this.nickname = nickname
    }

    var mobile: String? = null
    var avatar: String? = null
    var birthday: String? = null
    var name: String? = null
    var nickname: String? = null

    fun getPwdMobile(): String{
        val result: String
        result = if (mobile != null && mobile!!.length > 7) {
            mobile!!.substring(0, 3) + "****" + mobile!!.substring(
                mobile!!.length - 4,
                mobile!!.length
            )
        } else {
            mobile!!
        }
        return result
    }

}