package com.strong.ui

import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.strong.R
import com.strong.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    companion object{
        fun startMainActivity(){
            ActivityUtils.startActivity(MainActivity::class.java)
        }
    }

    override fun layoutId() = R.layout.activity_main

    override fun initData(bundle: Bundle?) {

    }


}