package com.strong.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import com.blankj.utilcode.util.ActivityUtils
import com.strong.base.BaseActivity

class MainActivity : BaseActivity() {

    companion object{
        fun startMainActivity(){
            ActivityUtils.startActivity(MainActivity::class.java)
        }
    }

    override fun initView() {
        setContent {
            MainLayout(name = "hello word")
        }
    }


    override fun initData(bundle: Bundle?) {

    }
}