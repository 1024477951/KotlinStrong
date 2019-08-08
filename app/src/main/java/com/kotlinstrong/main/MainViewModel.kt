package com.kotlinstrong.main

import androidx.lifecycle.MutableLiveData
import com.kotlinstrong.stronglib.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.kotlinstrong.bean.ArticleList
import com.kotlinstrong.bean.LoginBean

class MainViewModel : BaseViewModel() {

    private val repository by lazy { MainRepository() }

    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()

    val loginLiveData: MutableLiveData<LoginBean> = MutableLiveData()

//    fun getArticleList(page: Int) {
//        launch {
//            val result = withContext(Dispatchers.IO) { repository.getArticleList(page) }
//            executeResponse(result, { mArticleList.postValue(result.data) }, {})
//        }
//    }

    fun login() {
        launch {
            val result = withContext(Dispatchers.IO) { repository.login() }
            executeResponse(result, { loginLiveData.postValue(result.data) }, {})
        }
    }
}