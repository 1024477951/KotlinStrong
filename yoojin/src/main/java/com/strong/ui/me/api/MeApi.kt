package com.strong.ui.me.api

import com.strong.net.BaseResponse
import com.strong.baselib.bean.UserBean
import retrofit2.http.*

interface MeApi {

    @GET("user.json")
    suspend fun getUser(): BaseResponse<UserBean>

}