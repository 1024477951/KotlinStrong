package com.strong.ui


import com.strong.baselib.base.BaseViewModel

class MainViewModel : BaseViewModel() {

    private val repository by lazy { MainRepository() }

    val refresh: () -> Unit = {

    }
}