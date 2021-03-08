package com.strong.ui.splash.model

import com.strong.net.BaseRepository
import com.strong.net.BaseResponse
import com.strong.net.http.RetrofitClient
import com.strong.ui.splash.api.SplashApi
import com.strong.ui.splash.bean.SplashBean

class SplashRepository : BaseRepository() {

    suspend fun getSplash(): BaseResponse<SplashBean> {
        return apiCall {
            RetrofitClient.getService(SplashApi::class.java).getSplash()
        }
    }

}