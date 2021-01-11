package com.strong.utils.system

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.fragment.app.FragmentActivity
import java.security.NoSuchAlgorithmException

/**
 * created by YooJin.
 * date: 2021/1/8 17:38
 * desc:获取apk签名，测试工具类
 */
class SignaturesUtils {

    fun sHA1(activity: FragmentActivity): String? {
        try {
            val manager: PackageManager = activity?.packageManager!!
            val info: PackageInfo = manager.getPackageInfo(
                activity?.packageName!!, PackageManager.GET_SIGNATURES
            )
            if (null != info.signatures && info.signatures.size > 0) {
                val sign = info.signatures.get(0).toCharsString()
                return sign
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return null
    }

}