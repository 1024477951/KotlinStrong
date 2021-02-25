package com.strong.ui.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.ktx.immersionBar
import com.strong.R

abstract class BaseBindActivity<V : ViewDataBinding,VM : BaseViewModel> : AppCompatActivity() , LifecycleObserver {

    protected var tag: String = javaClass.simpleName
    lateinit var context: Context

    /** 加载布局 */
    abstract fun layoutId(): Int

    /** 初始化数据 */
    abstract fun initData(bundle: Bundle?)

    /** base model */
    abstract fun providerVMClass(): Class<VM>

    lateinit var binding: V
    lateinit var mViewModel: VM//lateinit : 编译期在检查时不要因为属性变量未被初始化而报错

    /** livedata 监听 */
    open fun modelObserve() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        binding = bindingView(layoutId())
        //初始化沉浸式
        initImmersionBar()
        initVM()
        modelObserve()
        initData(intent.extras)
        Log.d("current class name",javaClass.simpleName)
    }

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected open fun initImmersionBar() {
        //设置共同沉浸式样式
        immersionBar{
            //字体颜色为黑色(深色)，默认白色(亮色)
            statusBarDarkFont(true)
            navigationBarColor(R.color.transparent)
        }
    }

    private fun bindingView(@LayoutRes resId: Int): V {
        return DataBindingUtil.setContentView<V>(this, resId).apply {
            lifecycleOwner = this@BaseBindActivity
        }
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