package com.strong.baselib.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

/**
 * @author liuzhen
 * @date 2023/4/26
 */
abstract class BaseVMFragment<VM : BaseViewModel, VB : ViewBinding>: Fragment() {

    protected lateinit var mBinding: VB
    lateinit var mViewModel: VM

    abstract fun initView()
    abstract fun initData()

    /** base model */
    abstract fun providerVMClass(): Class<VM>

    /** liveData 监听 */
    open fun scribeObserve() {}

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = javaClass.genericSuperclass as ParameterizedType
        val cls = type.actualTypeArguments[1] as Class<*>
        try {
            val inflate: Method = cls.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            )
            mBinding = inflate.invoke(null, inflater, container, false) as VB
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initData()
        scribeObserve()
        super.onViewCreated(view, savedInstanceState)
    }

}