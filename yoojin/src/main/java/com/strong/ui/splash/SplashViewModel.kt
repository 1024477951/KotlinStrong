package com.strong.ui.splash

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.strong.ui.base.BaseViewModel
import com.strong.ui.splash.bean.SplashBean
import com.strong.ui.splash.model.SplashRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SplashViewModel : BaseViewModel() {

    private val repository by lazy { SplashRepository() }

    val bannerField = ObservableField<MutableList<SplashBean.SplashData>>()

    val splashLiveData: MutableLiveData<Int> = MutableLiveData()
    fun getSplashList() {
        launchOnUI {
            val result = withContext(Dispatchers.IO) {
                repository.getSplash()
            }
            result.data?.list?.let {
                bannerField.set(it)
                splashLiveData.postValue(it.size)
            }
        }
    }
}