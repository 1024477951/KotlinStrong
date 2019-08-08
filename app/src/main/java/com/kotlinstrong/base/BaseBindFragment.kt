package com.kotlinstrong.base

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProviders
import com.kotlinstrong.R
import com.kotlinstrong.stronglib.base.BaseFragment
import com.kotlinstrong.stronglib.base.BaseViewModel

abstract class BaseBindFragment<VM : BaseViewModel> : BaseFragment() , LifecycleObserver {

    lateinit var mViewModel: VM//lateinit : 编译期在检查时不要因为属性变量未被初始化而报错
    /** base model */
    open fun providerVMClass(): Class<VM>? = null
    /** base layout */
    override fun layoutId(): Int {
        return R.layout.layout_error
    }
    /** livedata 监听 */
    open fun modelObserve() {}

    override fun initData(bundle: Bundle?) {
        initVM()
        modelObserve()
    }

    private fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            mViewModel.let(lifecycle::addObserver)
        }
    }

    override fun onDestroy() {
        mViewModel.let {
            lifecycle.removeObserver(it)
        }
        super.onDestroy()
    }
}