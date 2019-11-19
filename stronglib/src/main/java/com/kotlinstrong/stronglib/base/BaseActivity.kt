package com.kotlinstrong.stronglib.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected var tag: String = javaClass.simpleName

    var context:Context? = null

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
        initData(intent.extras)
    }

}