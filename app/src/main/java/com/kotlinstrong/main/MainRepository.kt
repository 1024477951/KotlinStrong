package com.kotlinstrong.main

import com.kotlinstrong.http.RetrofitClient
import com.kotlinstrong.stronglib.base.BaseRepository
import com.kotlinstrong.stronglib.base.BaseResponse
import com.kotlinstrong.bean.ArticleList

class MainRepository : BaseRepository() {

    suspend fun getArticleList(page: Int): BaseResponse<ArticleList> {
        return apiCall { RetrofitClient.service.getHomeArticles(page) }
    }
}