package com.strong

import android.app.Application
import com.strong.baselib.base.LifecyclePrinter

/**
 * created by YooJin.
 * date: 2021/1/5 16:34
 */
class YooJinApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LifecyclePrinter(BuildConfig.VERSION_NAME).init(this)
    }
}