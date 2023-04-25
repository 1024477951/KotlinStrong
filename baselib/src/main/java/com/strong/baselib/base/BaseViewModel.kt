package com.strong.baselib.base


import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() , LifecycleObserver {

    //private val mException: MutableLiveData<Exception> = MutableLiveData()

    protected fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("BaseViewModel","onCleared")
    }

}