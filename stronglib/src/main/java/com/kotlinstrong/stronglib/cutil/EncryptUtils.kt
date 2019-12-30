package com.kotlinstrong.stronglib.cutil

import android.os.Environment
import android.util.Log
import com.blankj.utilcode.util.Utils
import java.io.File

/** 加密 */
class EncryptUtils {

    //属性声明都是静态的，方法并不是静态，必须通过@JvmStatic注解
    companion object {
        private const val TAG = "EncryptUtils"
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("Encrypt")
            System.loadLibrary("Signature")
        }

        val path = Utils.getApp().getExternalFilesDir("").absolutePath + File.separator

        /** 创建文件 */
        @JvmStatic
        external fun createFile (path: String)
        /** 加密 */
        @JvmStatic
        external fun encryption (filePath: String,encryptPath: String)
        /** 解密 */
        @JvmStatic
        external fun decryption (filePath: String,decryptionPath: String)
        /** 签名效验，返回验证结果 */
        @JvmStatic
        external fun checkSignature ():Boolean
        /** 文件分割 */
        @JvmStatic
        external fun fileSplit (splitFilePath: String,suffix: String,fileNum:Int)
        /** 合并文件 */
        @JvmStatic
        external fun fileMerge (splitFilePath: String,splitSuffix: String,mergeSuffix: String,fileNum:Int)

        /** 测试加解密 */
        @JvmStatic
        fun test(): String {
            val fileName = "testJni.txt"
            val encryptPath = encryption(fileName)
            decryption(encryptPath)
            return encryptPath
        }

        /** 加密 */
        @JvmStatic
        fun encryption(fileName: String): String {
            val normalPath = path + fileName
            val file = File(normalPath)
            Log.d(TAG, "normalPath：$normalPath file: ${file.exists()}")
            if (!file.exists()) {
                createFile(normalPath)
            }
            val encryptPath = path + "encryption_" + fileName
            encryption(normalPath, encryptPath)
            Log.d(TAG, "加密完成了...")
            return encryptPath
        }

        /** 解密 */
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

        /** 文件分割 */
        @JvmStatic
        fun fileSplit (){
            val splitFilePath = path + "testJni.txt"
            try {
                fileSplit(splitFilePath, ".s",3)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        /** 文件合并 */
        @JvmStatic
        fun fileMerge (){
            val splitFilePath = path + "testJni.txt"
            val splitSuffix = ".s"//保持跟切割格式一致
            val mergeSuffix = "_merge.txt"
            try {
                fileMerge(splitFilePath, splitSuffix, mergeSuffix, 3)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}