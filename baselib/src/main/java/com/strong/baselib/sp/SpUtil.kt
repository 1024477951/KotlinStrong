package com.strong.baselib.sp

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV
import java.util.*

object SpUtil {
    var mmkv: MMKV? = null

    init {
        mmkv = MMKV.defaultMMKV()
    }

    fun encode(key: String, value: Any?) {
        when (value) {
            is String -> mmkv?.encode(key, value)
            is Float -> mmkv?.encode(key, value)
            is Boolean -> mmkv?.encode(key, value)
            is Int -> mmkv?.encode(key, value)
            is Long -> mmkv?.encode(key, value)
            is Double -> mmkv?.encode(key, value)
            is ByteArray -> mmkv?.encode(key, value)
            else -> return
        }
    }

    fun <T : Parcelable> encode(key: String, t: T?) {
        if (t == null) {
            return
        }
        mmkv?.encode(key, t)
    }

    fun encode(key: String, sets: Set<String>?) {
        if (sets == null) {
            return
        }
        mmkv?.encode(key, sets)
    }

    fun decodeInt(key: String): Int? {
        return mmkv?.decodeInt(key, 0)
    }

    fun decodeDouble(key: String): Double? {
        return mmkv?.decodeDouble(key, 0.00)
    }

    fun decodeLong(key: String): Long? {
        return mmkv?.decodeLong(key, 0L)
    }

    fun decodeBoolean(key: String): Boolean? {
        return mmkv?.decodeBool(key, false)
    }

    fun decodeBoolean(key: String, defaultValue: Boolean): Boolean? {
        return mmkv?.decodeBool(key, defaultValue)
    }

    fun decodeFloat(key: String): Float? {
        return mmkv?.decodeFloat(key, 0F)
    }

    fun decodeByteArray(key: String): ByteArray? {
        return mmkv?.decodeBytes(key)
    }

    fun decodeString(key: String): String? {
        return mmkv?.decodeString(key, "")
    }

    fun <T : Parcelable> decodeParcelable(key: String, tClass: Class<T>): T? {
        return mmkv?.decodeParcelable(key, tClass)
    }

    fun <T> setDataList(list: List<T>?, key: String) {
        if (list == null || list.isEmpty()) { //清空
            return
        } else {
            val strJson = Gson().toJson(list)
            mmkv?.putString(key, strJson)
        }
    }

    fun <T> getDataList(key: String): MutableList<T>? {
        var datalist: MutableList<T> = mutableListOf()
        val strJson: String = mmkv?.decodeString(key) ?: return datalist
        val gson = Gson()
        datalist = gson.fromJson(strJson, object : TypeToken<List<T>?>() {}.type)
        return datalist
    }


    fun decodeStringSet(key: String): Set<String>? {
        return mmkv?.decodeStringSet(key, Collections.emptySet())
    }

    fun containsKey(key: String): Boolean {
        return mmkv?.containsKey(key) ?: false
    }

    fun removeKey(key: String) {
        mmkv?.removeValueForKey(key)
    }

    fun clearAll() {
        mmkv?.clearAll()
    }
}

