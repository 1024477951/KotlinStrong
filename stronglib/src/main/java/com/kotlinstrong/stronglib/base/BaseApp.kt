package com.kotlinstrong.stronglib.base

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

open class BaseApp : Application(){

    companion object {
        private var _context:Application? = null
        fun getContext(): Context {
            return _context!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        _context = this
    }

    private fun startKoin() {
        //start koin
//        org.koin.core.context.startKoin {
//            androidLogger()
//            androidContext(this@BaseApp)
//            modules(appModule)
//        }
    }

}