package com.strong.baselib.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ktx.immersionBar
import com.strong.baselib.R
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

/**
 * @author liuzhen
 * @date 2023/4/24
 */
abstract class BaseVMActivity<VM : BaseViewModel, VB : ViewBinding>: AppCompatActivity() {

    protected lateinit var mBinding: VB
    lateinit var mViewModel: VM

    abstract fun initView()
    abstract fun initData()

    /** base model */
    abstract fun providerVMClass(): Class<VM>

    /** liveData 监听 */
    open fun scribeObserve() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewBinding()
        initImmersionBar()
        initVM()
        initView()
        initData()
        scribeObserve()
    }

    protected open fun getViewBinding() {
        val type = javaClass.genericSuperclass as ParameterizedType?
        val cls = type?.actualTypeArguments?.get(1) as Class<*>
        try {
            val inflate: Method = cls.getMethod("inflate", LayoutInflater::class.java)
            mBinding = inflate.invoke(null, layoutInflater) as VB
            setContentView(mBinding.root)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
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
            statusBarColor(R.color.white)
            navigationBarColor(R.color.white)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
    }

    private fun initVM() {
        mViewModel = ViewModelProvider(this)[providerVMClass()]
        mViewModel.let(lifecycle::addObserver)
    }

}