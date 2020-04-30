package com.kotlinstrong.stronglib.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val mSysThemeConfig =
            newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (mSysThemeConfig) {
            Configuration.UI_MODE_NIGHT_NO -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }
        // 需调用 recreate() ，从而使更改生效
        recreate();
    }

}