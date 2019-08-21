package com.kotlinstrong.main

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.JsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kotlinstrong.stronglib.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.kotlinstrong.bean.ArticleList
import com.kotlinstrong.bean.LoginBean

class MainViewModel : BaseViewModel() {

    private val tag: String = "MainViewModel"

    private val repository by lazy { MainRepository() }

    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()

    val loginLiveData: MutableLiveData<LoginBean> = MutableLiveData()

    fun getArticleList(page: Int) {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getArticleList(page) }
            executeResponse(result, { }, { mArticleList.postValue(result.data) }
            )
        }
    }

    fun login() {
        launch {
            val result = withContext(Dispatchers.IO) { repository.login() }
            executeResponse(result, { loginLiveData.postValue(result.data) }, { LogUtils.e(tag, GsonUtils.toJson(result)) })
        }
    }
}