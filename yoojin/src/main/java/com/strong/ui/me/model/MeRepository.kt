package com.strong.ui.me.model

import com.strong.net.BaseRepository
import com.strong.net.BaseResponse
import com.strong.net.http.RetrofitClient
import com.strong.ui.me.api.MeApi
import com.strong.baselib.bean.UserBean

class MeRepository : BaseRepository() {

    suspend fun getUser(): BaseResponse<UserBean> {
        return apiCall {
            RetrofitClient.getService(MeApi::class.java).getUser()
        }
    }

}