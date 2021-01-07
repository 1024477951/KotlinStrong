package com.strong.ui.home.model

import com.strong.net.BaseRepository
import com.strong.net.BaseResponse
import com.strong.net.http.RetrofitClient
import com.strong.ui.home.api.HomeApi
import com.strong.ui.home.bean.ArticleList

class HomeRepository : BaseRepository() {

    suspend fun getTestList(): BaseResponse<ArticleList> {
        return apiCall {
            RetrofitClient.getService(HomeApi::class.java).getTestLists(0)
        }
    }

}