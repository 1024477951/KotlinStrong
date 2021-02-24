package com.strong.ui.sort

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.strong.ui.base.BaseViewModel
import com.strong.ui.home.bean.ArticleList
import com.strong.ui.sort.model.SortRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SortViewModel : BaseViewModel() {

    private val repository by lazy { SortRepository() }

    val testLiveData: MutableLiveData<ArticleList> = MutableLiveData()

    val refreshHome: ()-> Unit = {

    }

    fun getTestList() {
        launchOnUI {
            val result = withContext(Dispatchers.IO) {
                repository.getTestList()
            }
            testLiveData.postValue(result.data)
            Log.e("result",GsonUtils.toJson(result.data))
        }
    }

}