package com.strong.utils

import android.Manifest
import android.app.Activity
import android.os.Build
import com.blankj.utilcode.util.PermissionUtils

/**
 * created by YooJin.
 * date: 2021/1/22 17:50
 * desc:
 */
class PermissionManagerUtils {

    companion object {

        private const val requestCode = 0x1024

        fun checkFilePermission(activity: Activity): Boolean{
            if(PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activity.requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
                    return false
                }
            }
            return true
        }

    }
}