package com.strong.ui

import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.strong.base.BaseActivity

class MainActivity : BaseActivity() {

    companion object{
        fun startMainActivity(){
            ActivityUtils.startActivity(MainActivity::class.java)
        }
    }

    override fun initView() {

    }


    override fun initData(bundle: Bundle?) {

    }
}