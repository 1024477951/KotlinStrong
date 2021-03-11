package com.strong.ui.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.components.SimpleImmersionFragment
import com.gyf.immersionbar.ktx.immersionBar

abstract class BaseImmersionBindFragment<V : ViewDataBinding, VM : BaseViewModel> : SimpleImmersionFragment() , LifecycleObserver {

    /** 加载布局 */
    abstract fun layoutId(): Int

    /** 状态栏 */
    abstract fun statusView(): View

    /** 初始化数据 */
    abstract fun initData(bundle: Bundle?)

    /** base model */
    abstract fun providerVMClass(): Class<VM>

    lateinit var binding: V
    lateinit var mViewModel: VM//lateinit : 编译期在检查时不要因为属性变量未被初始化而报错

    /** livedata 监听 */
    open fun modelObserve() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingView(inflater, layoutId(), container)
        return binding.root
    }

    private fun bindingView(
        inflater: LayoutInflater,
        @LayoutRes layoutId: Int,
        container: ViewGroup?
    ): V = DataBindingUtil.inflate<V>(inflater, layoutId, container, false).apply {
        lifecycleOwner = this@BaseImmersionBindFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initStatusBar()
        initVM()
        modelObserve()
        initData(arguments)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initVM() {
        //ViewModelProviders过时，现在google推荐直接使用ViewModelProvider创建
        mViewModel = ViewModelProvider(this).get(providerVMClass())
        mViewModel.let(lifecycle::addObserver)
    }

    private fun initStatusBar(){
        immersionBar {
            statusBarView(statusView())
        }
    }

    override fun initImmersionBar() {
        immersionBar {
            keyboardEnable(true)
            //字体颜色为黑色(深色)，默认白色(亮色)
            statusBarDarkFont(true)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        initStatusBar()
    }

    override fun onDestroy() {
        mViewModel.let {
            lifecycle.removeObserver(it)
        }
        super.onDestroy()
    }
}