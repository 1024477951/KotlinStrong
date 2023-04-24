package com.strong.ui


import com.strong.ui.base.BaseViewModel

class MainViewModel : BaseViewModel() {

    private val repository by lazy { MainRepository() }

    val refresh: () -> Unit = {

    }
}