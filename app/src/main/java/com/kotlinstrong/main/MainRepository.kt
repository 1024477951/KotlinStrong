package com.kotlinstrong.main

import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableMap
import com.kotlinstrong.api.ArticleApi
import com.kotlinstrong.api.LoginApi
import com.kotlinstrong.bean.ArticleList
import com.kotlinstrong.http.RetrofitClient
import com.kotlinstrong.stronglib.base.BaseRepository
import com.kotlinstrong.stronglib.base.BaseResponse
import com.kotlinstrong.bean.LoginBean

class MainRepository : BaseRepository() {

    /**  列表接口 */
    suspend fun getArticleList(page: Int): BaseResponse<ArticleList> {
        return apiCall {
            RetrofitClient.getService(ArticleApi::class.java).getHomeArticles(page)
        }
    }

    private val loginMap: ObservableMap<String,Any> = ObservableArrayMap()
    /** 登录 */
    suspend fun login(): BaseResponse<LoginBean> {
        return apiCall {
            loginMap["username"] = "xxxx"
            loginMap["password"] = 123456
            RetrofitClient.getService(LoginApi::class.java).toLogin(loginMap)
        }
    }
}