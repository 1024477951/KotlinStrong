package com.strong.ui.sort

import com.strong.baselib.base.BaseViewModel
import com.strong.ui.sort.model.SortRepository

class SortViewModel : BaseViewModel() {

    private val repository by lazy { SortRepository() }

    val refreshHome: () -> Unit = {

    }

    fun getTestList() {

    }

}