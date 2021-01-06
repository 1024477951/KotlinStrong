package com.kotlinstrong.api

import androidx.lifecycle.LiveData
import com.kotlinstrong.stronglib.base.BaseResponse
import com.kotlinstrong.bean.ArticleList
import com.kotlinstrong.bean.RepoSearch
import com.kotlinstrong.stronglib.factory.ApiResponse
import retrofit2.http.*

interface ArticleApi {
    /**
     * 文章详情
     */
//    @JvmSuppressWildcards
    @Headers("url_head:article")
    @GET("/article/list/{page}/json")
    fun getHomeArticles(@Path("page") page: Int): LiveData<ApiResponse<BaseResponse<MenuBean>>>

    @GET("search/repositories")
    suspend fun searchRepos(@Query("q") query: String): LiveData<ApiResponse<RepoSearch>>
}