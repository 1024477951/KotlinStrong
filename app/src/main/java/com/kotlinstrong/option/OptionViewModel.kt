package com.kotlinstrong.option

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

class OptionViewModel : BaseViewModel() {

    private val tag: String = "OptionViewModel"

    private val repository by lazy { OptionRepository() }

}