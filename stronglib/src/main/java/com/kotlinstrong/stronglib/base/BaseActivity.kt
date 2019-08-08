package com.kotlinstrong.stronglib.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    fun start(context: Context) {
        start(context, null)
    }

    fun start(context: Context, extras: Intent?) {
        val intent = Intent()
        intent.setClass(context, this::class.java)
        if (extras != null) {
            intent.putExtras(extras)
        }
        context.startActivity(intent)
    }

    var context:Context? = null

    /**
     *  加载布局
     */
    abstract fun layoutId(): Int
    /**
     * 初始化数据
     */
    abstract fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContentView(layoutId())
        initData()
    }
}