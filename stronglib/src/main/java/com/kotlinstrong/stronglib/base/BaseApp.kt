package com.kotlinstrong.stronglib.base

import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.Utils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

open class BaseApp : Application(){

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }

}