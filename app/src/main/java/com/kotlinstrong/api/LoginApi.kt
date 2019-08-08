package com.kotlinstrong.api

import com.kotlinstrong.stronglib.base.BaseResponse
import com.kotlinstrong.bean.LoginBean
import retrofit2.http.*

interface LoginApi {
    @FormUrlEncoded
    @POST("login/old")
    suspend fun toLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): BaseResponse<LoginBean>
}