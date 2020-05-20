package com.kotlinstrong.stronglib.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.blankj.utilcode.util.Utils

/** 无侵入式初始化(避免耗时操作) */
class ApplicationContextProvider : ContentProvider() {

    companion object{
        var mContext: Context? = null
    }

    override fun onCreate(): Boolean {
        mContext = context
        //初始化全局Context提供者
        Utils.init(ContextProvider.get()?.getApplication())
        return false
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }
}