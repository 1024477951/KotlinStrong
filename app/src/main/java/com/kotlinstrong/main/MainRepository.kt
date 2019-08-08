package com.kotlinstrong.main

import com.kotlinstrong.api.ArticleApi
import com.kotlinstrong.api.LoginApi
import com.kotlinstrong.http.RetrofitClient
import com.kotlinstrong.stronglib.base.BaseRepository
import com.kotlinstrong.stronglib.base.BaseResponse
import com.kotlinstrong.bean.ArticleList
import com.kotlinstrong.bean.LoginBean

class MainRepository : BaseRepository() {
//    /**  访问接口 */
//    suspend fun getArticleList(page: Int): BaseResponse<ArticleList> {
//        return apiCall { RetrofitClient.getService(ArticleApi::class.java).getHomeArticles(page) }
//    }

    /** 登录 */
    suspend fun login(): BaseResponse<LoginBean> {
        return apiCall { RetrofitClient.getService(LoginApi::class.java).toLogin("s645","123456") }
    }
}