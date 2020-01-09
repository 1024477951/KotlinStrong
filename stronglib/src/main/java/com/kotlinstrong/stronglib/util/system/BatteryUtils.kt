package com.kotlinstrong.stronglib.util.system

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.Utils

/** 原文链接：https://juejin.im/post/5dfaeccbf265da33910a441d 文章特别好，还关注了公众号 */
class BatteryUtils {

    companion object{

        /** 是否加入电池优化 */
        @RequiresApi(api = Build.VERSION_CODES.M)
        fun isIgnoringBatteryOptimizations(): Boolean {
            var isIgnoring = false
            val powerManager =
                Utils.getApp().getSystemService(Context.POWER_SERVICE) as PowerManager?
            if (powerManager != null) {
                isIgnoring = powerManager.isIgnoringBatteryOptimizations(Utils.getApp().packageName)
            }
            Log.e("BatteryUtils","isIgnoring $isIgnoring")
            return isIgnoring
        }

        /** 申请加入优化 */
        @RequiresApi(api = Build.VERSION_CODES.M)
        fun requestIgnoreBatteryOptimizations(context: Fragment) {
            try {
                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                intent.data = Uri.parse("package:${Utils.getApp().packageName}")
                context.startActivityForResult(intent,Activity.RESULT_FIRST_USER)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /**
         * 跳转到指定应用的首页
         */
        private fun showActivity(packageName: String) {
            val intent: Intent = Utils.getApp().packageManager.getLaunchIntentForPackage(packageName)
            Utils.getApp().startActivity(intent)
        }

        /**
         * 跳转到指定应用的指定页面
         */
        private fun showActivity(packageName: String, activityDir: String) {
            val intent = Intent()
            intent.component = ComponentName(packageName, activityDir)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            Utils.getApp().startActivity(intent)
        }
        /** 应用加入了后台运行白名单，仍然可能会被厂商自己的后台管理干掉,尝试把应用加入厂商系统的后台管理白名单，可以进一步降低进程被杀的概率 */
        fun setAppIgnore(){
            when {
                isHuawei() -> {
                    goHuaweiSetting()
                }
                isXiaomi() -> {
                    goXiaomiSetting()
                }
                isOPPO() -> {
                    goOPPOSetting()
                }
                isVIVO() -> {
                    goVIVOSetting()
                }
                isMeizu() -> {
                    goMeizuSetting()
                }
                isSamsung() -> {
                    goSamsungSetting()
                }
                isLeTV() -> {
                    goLetvSetting()
                }
                isSmartisan() -> {
                    goSmartisanSetting()
                }
            }
        }

        private fun isHuawei(): Boolean {
            return if (Build.BRAND == null) {
                false
            } else {
                Build.BRAND.toLowerCase() == "huawei" || Build.BRAND.toLowerCase() == "honor"
            }
        }

        private fun goHuaweiSetting() {
            try {
                showActivity(
                    "com.huawei.systemmanager",
                    "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
                )
            } catch (e: java.lang.Exception) {
                showActivity(
                    "com.huawei.systemmanager",
                    "com.huawei.systemmanager.optimize.bootstart.BootStartActivity"
                )
            }
        }

        private fun isXiaomi(): Boolean {
            return Build.BRAND != null && Build.BRAND.toLowerCase() == "xiaomi"
        }

        private fun goXiaomiSetting() {
            showActivity(
                "com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity"
            )
        }

        private fun isOPPO(): Boolean {
            return Build.BRAND != null && Build.BRAND.toLowerCase() == "oppo"
        }

        private fun goOPPOSetting() {
            try {
                showActivity("com.coloros.phonemanager")
            } catch (e1: java.lang.Exception) {
                try {
                    showActivity("com.oppo.safe")
                } catch (e2: java.lang.Exception) {
                    try {
                        showActivity("com.coloros.oppoguardelf")
                    } catch (e3: java.lang.Exception) {
                        showActivity("com.coloros.safecenter")
                    }
                }
            }
        }

        private fun isVIVO(): Boolean {
            return Build.BRAND != null && Build.BRAND.toLowerCase() == "vivo"
        }

        private fun goVIVOSetting() {
            showActivity("com.iqoo.secure")
        }

        private fun isMeizu(): Boolean {
            return Build.BRAND != null && Build.BRAND.toLowerCase() == "meizu"
        }

        private fun goMeizuSetting() {
            showActivity("com.meizu.safe")
        }

        private fun isSamsung(): Boolean {
            return Build.BRAND != null && Build.BRAND.toLowerCase() == "samsung"
        }

        private fun goSamsungSetting() {
            try {
                showActivity("com.samsung.android.sm_cn")
            } catch (e: java.lang.Exception) {
                showActivity("com.samsung.android.sm")
            }
        }

        private fun isLeTV(): Boolean {
            return Build.BRAND != null && Build.BRAND.toLowerCase() == "letv"
        }

        private fun goLetvSetting() {
            showActivity(
                "com.letv.android.letvsafe",
                "com.letv.android.letvsafe.AutobootManageActivity"
            )
        }

        private fun isSmartisan(): Boolean {
            return Build.BRAND != null && Build.BRAND.toLowerCase() == "smartisan"
        }

        private fun goSmartisanSetting() {
            showActivity("com.smartisanos.security")
        }
    }
}