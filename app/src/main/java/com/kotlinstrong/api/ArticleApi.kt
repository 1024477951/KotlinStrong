package com.kotlinstrong.api

import com.kotlinstrong.stronglib.base.BaseResponse
import io.reactivex.Single
import com.kotlinstrong.bean.ArticleList
import retrofit2.http.*

interface ArticleApi {
    /**
     * 文章详情
     */
    @JvmSuppressWildcards
    @Headers("url_head:article")
    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): BaseResponse<ArticleList>
}