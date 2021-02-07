package com.strong.ui.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.strong.R


abstract class BaseActivity : AppCompatActivity() {

    protected var tag: String = javaClass.simpleName

    lateinit var context:Context

    /**
     *  加载布局
     */
    abstract fun layoutId(): Int
    /**
     * 初始化数据
     */
    abstract fun initData(bundle: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContentView(layoutId())
        //初始化沉浸式
        initImmersionBar()
        initData(intent.extras)
        Log.d("current class name", javaClass.simpleName)
    }

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected open fun initImmersionBar() {
        //设置共同沉浸式样式
        ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).init()
    }

}