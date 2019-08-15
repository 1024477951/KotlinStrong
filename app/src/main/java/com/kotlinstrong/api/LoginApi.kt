package com.kotlinstrong.api

import com.kotlinstrong.stronglib.base.BaseResponse
import com.kotlinstrong.bean.LoginBean
import retrofit2.http.*

interface LoginApi {

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("login/old")
    suspend fun toLogin(
        @FieldMap map: Map<String, Any>
    ): BaseResponse<LoginBean>
}