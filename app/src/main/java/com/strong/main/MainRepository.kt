package com.kotlinstrong.main

import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableMap
import androidx.lifecycle.LiveData
import com.kotlinstrong.api.ArticleApi
import com.kotlinstrong.bean.ArticleList
import com.kotlinstrong.bean.RepoSearch
import com.kotlinstrong.http.RetrofitClient
import com.kotlinstrong.stronglib.base.BaseRepository
import com.kotlinstrong.stronglib.base.BaseResponse
import com.kotlinstrong.stronglib.factory.ApiResponse

class MainRepository : BaseRepository() {

    /**  列表接口 */
    suspend fun getArticleList(page: Int): LiveData<ApiResponse<BaseResponse<ArticleList>>> {
        return apiCall { RetrofitClient.getService(ArticleApi::class.java).getHomeArticles(page) }
    }

    fun test(page: Int): LiveData<ApiResponse<BaseResponse<ArticleList>>> {
        return RetrofitClient.getService(ArticleApi::class.java).getHomeArticles(page)
    }

    suspend fun searchRepos(): LiveData<ApiResponse<RepoSearch>> {
        return RetrofitClient.getService(ArticleApi::class.java).searchRepos("foo")
    }

    private val loginMap: ObservableMap<String,Any> = ObservableArrayMap()
    /** 登录 */
    suspend fun login() {
//        return apiCall {
//            loginMap["username"] = "xxxx"
//            loginMap["password"] = 123456
//            RetrofitClient.getService(LoginApi::class.java).toLogin(loginMap)
//        }
    }
}