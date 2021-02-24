package com.strong.ui.sort.model

import com.strong.net.BaseRepository
import com.strong.net.BaseResponse
import com.strong.net.http.RetrofitClient
import com.strong.ui.home.bean.ArticleList
import com.strong.ui.sort.api.SortApi

class SortRepository : BaseRepository() {

    suspend fun getTestList(): BaseResponse<ArticleList> {
        return apiCall {
            RetrofitClient.getService(SortApi::class.java).getTestLists(0)
        }
    }

}