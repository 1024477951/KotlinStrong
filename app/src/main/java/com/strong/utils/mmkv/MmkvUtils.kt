package com.strong.utils.mmkv

import com.tencent.mmkv.MMKV


/**
 * created by YooJin.
 * date: 2021/3/11 10:58
 * @Desc:MmkvUtils
 */
class MmkvUtils {

    companion object {
        private val instance by lazy { MmkvUtils() }
        fun createMmkvUtils(): MmkvUtils = instance
    }

    fun getMkv(): MMKV? {
        return MMKV.defaultMMKV()
    }
    /** 区别存储 */
    fun getMkv(id: String): MMKV? {
        return MMKV.mmkvWithID(id)
    }
    /** 多进程访问 */
    fun getMultiMkv(id: String): MMKV? {
        return MMKV.mmkvWithID(id, MMKV.MULTI_PROCESS_MODE)
    }

    fun put(key: String,value: Int) {
        getMkv()?.run {
            encode(key,value)
        }
    }

    fun put(key: String,value: String) {
        getMkv()?.run {
            encode(key,value)
        }
    }

    fun put(key: String,value: Boolean) {
        getMkv()?.run {
            encode(key,value)
        }
    }

    fun put(key: String,value: Double) {
        getMkv()?.run {
            encode(key,value)
        }
    }

    fun getInt(key: String): Int? {
        return getMkv()?.run {
            decodeInt(key,0)
        }
    }

    fun getString(key: String): String? {
        return getMkv()?.run {
            decodeString(key,null)
        }
    }

    fun getBoolean(key: String): Boolean? {
        return getMkv()?.run {
            decodeBool(key,false)
        }
    }

    fun getDouble(key: String): Double? {
        return getMkv()?.run {
            decodeDouble(key,0.0)
        }
    }

}