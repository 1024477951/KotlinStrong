package com.strong.ui

import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.strong.R
import com.strong.databinding.ActivityMainBinding
import com.strong.ui.base.BaseBindActivity

class MainActivity : BaseBindActivity<ActivityMainBinding, MainViewModel>() {

    companion object{
        fun startMainActivity(){
            ActivityUtils.startActivity(MainActivity::class.java)
        }
    }


    override fun layoutId() = R.layout.activity_main

    override fun providerVMClass() = MainViewModel::class.java

    override fun initData(bundle: Bundle?) {

    }

}