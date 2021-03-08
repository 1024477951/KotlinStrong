package com.strong.ui.splash.api

import com.strong.net.BaseResponse
import com.strong.ui.home.bean.ArticleList
import com.strong.ui.splash.bean.SplashBean
import retrofit2.http.*

interface SplashApi {

    /**
     * 启动页
     */
    @GET("splash.json")
    suspend fun getSplash(): BaseResponse<SplashBean>

}