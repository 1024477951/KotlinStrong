package com.strong.ui.home.api

import com.strong.net.BaseResponse
import com.strong.ui.home.bean.BannerBean
import retrofit2.http.*

interface HomeApi {

    /**
     * banner
     */
    @GET("banner.json")
    suspend fun getBanner(): BaseResponse<BannerBean>

}