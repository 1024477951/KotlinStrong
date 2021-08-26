package com.strong.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider

abstract class BaseBindFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment() , LifecycleObserver {

    /** 加载布局 */
    abstract fun layoutId(): Int

    /** 初始化数据 */
    abstract fun initData(bundle: Bundle?)

    /** base model */
    abstract fun providerVMClass(): Class<VM>

    lateinit var binding: V
    lateinit var mViewModel: VM//lateinit : 编译期在检查时不要因为属性变量未被初始化而报错

    /** liveData 监听 */
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
        lifecycleOwner = this@BaseBindFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

    override fun onDestroy() {
        mViewModel.let {
            lifecycle.removeObserver(it)
        }
        super.onDestroy()
    }
}