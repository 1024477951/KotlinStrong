package com.strong.ui.sort.api

import com.strong.net.BaseResponse
import com.strong.ui.home.bean.ArticleList
import retrofit2.http.*

interface SortApi {

    /**
     * 文章详情
     */
    @GET("/article/list/{page}/json")
    suspend fun getTestLists(@Path("page") page: Int): BaseResponse<ArticleList>

}