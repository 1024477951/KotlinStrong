package com.strong.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
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