package com.kotlinstrong.main

import androidx.lifecycle.MutableLiveData
import com.kotlinstrong.stronglib.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.kotlinstrong.bean.ArticleList

class MainViewModel : BaseViewModel() {

    private val repository by lazy { MainRepository() }

    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()

    fun getArticleList(page: Int) {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getArticleList(page) }
            executeResponse(result, { mArticleList.postValue(result.data) }, {})
        }
    }
}