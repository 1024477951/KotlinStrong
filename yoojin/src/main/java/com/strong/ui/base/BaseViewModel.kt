package com.strong.ui.base


import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() , LifecycleObserver {

    //private val mException: MutableLiveData<Exception> = MutableLiveData()

    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }
    /**  withContext : 切换协程
     *   CoroutineScope : 协程的作用域，可以管理其域内的所有协程
     * */
    suspend fun <T> launchIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

}