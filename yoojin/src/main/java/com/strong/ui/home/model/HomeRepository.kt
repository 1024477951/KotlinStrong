package com.strong.ui.home.model

import com.strong.net.BaseRepository
import com.strong.net.BaseResponse
import com.strong.net.http.RetrofitClient
import com.strong.ui.home.api.HomeApi
import com.strong.ui.home.bean.BannerBean

class HomeRepository : BaseRepository() {

    suspend fun getBanner(): BaseResponse<BannerBean> {
        return apiCall {
            RetrofitClient.getService(HomeApi::class.java).getBanner()
        }
    }

}