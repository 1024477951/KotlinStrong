package com.strong.baselib.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import java.text.SimpleDateFormat
import java.util.*

private const val TAG_UI = "Strong_UI"

/**
 * 全局监听并打印 Application, Activity, Fragment生命周期
 */
class LifecyclePrinter(
    private val buildVersion: String
) : LifecycleObserver {

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks())
    }

    @SuppressLint("SimpleDateFormat")
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onApplicationCreate() {
        val log = """
            #################### APP CREATED ####################
            *********************************************************
            App Start Time     : ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())}
            Device Manufacturer: ${Build.MANUFACTURER}
            Device Model       : ${Build.MODEL}
            Android Version    : ${Build.VERSION.RELEASE}
            Android SDK        : ${Build.VERSION.SDK_INT}
            App PackageName    : ${AppUtils.getAppPackageName()}
            App VersionName    : ${AppUtils.getAppVersionName()}
            App VersionCode    : ${AppUtils.getAppVersionCode()}
            App BuildVersion   : $buildVersion
            *********************************************************
            """.trimIndent()
        LogUtils.i(log)
    }
}

private class ActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.i(TAG_UI," onActivityCreated -> $activity")
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                FragmentLifecycleCallbacks(), true
            )
        }
    }

    override fun onActivityStarted(activity: Activity) {
        Log.i(TAG_UI," onActivityStarted -> $activity")
    }

    override fun onActivityResumed(activity: Activity) {
        Log.i(TAG_UI," onActivityResumed -> $activity")
    }

    override fun onActivityPaused(activity: Activity) {
        Log.i(TAG_UI," onActivityPaused -> $activity")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.i(TAG_UI," onActivityStopped -> $activity")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        Log.i(TAG_UI," onActivityDestroyed -> $activity")
    }
}

private class FragmentLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
        printMethod(f, "onFragmentCreated")
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        printMethod(f, "onFragmentDestroyed")
    }

    private fun printMethod(f: Fragment, methodName: String) {
        val className = f.javaClass.simpleName
        if (!IGNORED_FRAGMENTS.contains(className)) {
            Log.i(TAG_UI," $methodName -> $f")
        }
    }
}

// 不需要打印生命周期的fragment，比如有些用来辅助感知生命周期的fragment
private val IGNORED_FRAGMENTS = listOf(
    "SupportRequestManagerFragment", // Glide
    "SupportRequestBarManagerFragment", // ImmersionBar
)