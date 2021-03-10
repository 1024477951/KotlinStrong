package com.strong.ui.me.api

import com.strong.net.BaseResponse
import com.strong.ui.me.bean.UserBean
import retrofit2.http.*

interface MeApi {

    /**
     * banner
     */
    @GET("user.json")
    suspend fun getUser(): BaseResponse<UserBean>

}