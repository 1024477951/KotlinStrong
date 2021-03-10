package com.strong.ui.sort

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.strong.ui.base.BaseViewModel
import com.strong.ui.sort.model.SortRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SortViewModel : BaseViewModel() {

    private val repository by lazy { SortRepository() }

    val refreshHome: ()-> Unit = {

    }

    fun getTestList() {

    }

}