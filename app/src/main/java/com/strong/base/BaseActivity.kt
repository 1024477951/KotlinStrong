package com.strong.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity


abstract class BaseActivity : ComponentActivity() {

    protected var tag: String = javaClass.simpleName

    lateinit var context:Context

    /**
     *  加载布局
     */
    abstract fun initView()
    /**
     * 初始化数据
     */
    abstract fun initData(bundle: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        initView()
        initData(intent.extras)
        Log.d("current class name", javaClass.simpleName)
    }

}