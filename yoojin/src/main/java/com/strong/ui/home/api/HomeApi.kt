package com.strong.ui.home.api

import com.strong.net.BaseResponse
import com.strong.ui.home.bean.ArticleList
import retrofit2.http.*

interface HomeApi {

    /**
     * 文章详情
     */
    @GET("/article/list/{page}/json")
    suspend fun getTestLists(@Path("page") page: Int): BaseResponse<ArticleList>

}