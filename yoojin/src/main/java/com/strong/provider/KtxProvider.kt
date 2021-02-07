package com.strong.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.blankj.utilcode.util.Utils

/**
 * created by YooJin.
 * date: 2021/1/5 17:01
 * desc:无侵入式初始化
 */
class KtxProvider : ContentProvider() {

    companion object{
        lateinit var mContext: Context
    }

    override fun onCreate(): Boolean {
        mContext = context!!
        //初始化全局Context提供者
        Utils.init(ContextProvider.get()?.getApplication())
        return false
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }
}