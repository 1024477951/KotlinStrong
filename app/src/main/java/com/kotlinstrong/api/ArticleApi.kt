package com.kotlinstrong.api

import com.kotlinstrong.stronglib.base.BaseResponse
import io.reactivex.Single
import com.kotlinstrong.bean.Article
import com.kotlinstrong.bean.ArticleList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleApi {
    /**
     * 文章详情
     */
    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): BaseResponse<ArticleList>
}