package com.kotlinstrong.stronglib.cutil

import android.os.Environment
import android.util.Log
import java.io.File

/** 加密 */
class EncryptUtils {

//属性声明都是静态的，方法并不是静态，必须通过@JvmStatic注解

    companion object {
        private const val TAG = "EncryptUtils"
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("encrypt")
        }

        val path = Environment.getExternalStorageDirectory().absolutePath + File.separator

        /**
         * 创建文件
         */
        @JvmStatic
        external fun createFile (path: String)
        /**
         * 对一个字符串进行加密
         */
        @JvmStatic
        external fun encryption (filePath: String,encryptPath: String)
        /**
         * 对一个字符串进行加密
         */
        @JvmStatic
        external fun decryption (filePath: String,decryptionPath: String)

        /**
         * 测试加解密
         */
        @JvmStatic
        fun test(): String {
            val fileName = "testJni.txt"
            val encryptPath = encryption(fileName)
            decryption(encryptPath)
            return encryptPath
        }

        /**
         * 加密
         */
        @JvmStatic
        fun encryption(fileName: String): String {
            val normalPath = path + fileName
            val file = File(normalPath)
            if (!file.exists()) {
                createFile(normalPath)
            }
            val encryptPath = path + "encryption_" + fileName
            encryption(normalPath, encryptPath)
            Log.d(TAG, "加密完成了...")
            return encryptPath
        }

        /**
         * 解密
         */
        @JvmStatic
        fun decryption(encryptPath: String) {
            if (!File(encryptPath).exists()) {
                Log.d(TAG, "解密文件不存在")
                return
            }
            val decryptPath = encryptPath.replace("encryption_", "decryption_")
            decryption(encryptPath, decryptPath)
            Log.d(TAG, "解密完成了...")
        }
    }
}