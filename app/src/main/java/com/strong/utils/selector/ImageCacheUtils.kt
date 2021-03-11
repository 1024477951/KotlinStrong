package com.strong.utils.selector

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import java.io.File

/**
 * created by YooJin.
 * date: 2021/3/2 16:19
 * @describe：获取图片缓存
 */
class ImageCacheUtils {

    companion object{
        /**
         * 根据url获取图片缓存
         * Glide 4.x请调用此方法
         * 注意：此方法必须在子线程中进行
         *
         * @param context
         * @param url
         * @return File?
         */
        fun getCacheFileTo4x(context: Context, url: String): File? {
            var file : File? = null
            try {
                file = Glide.with(context).downloadOnly().load(url).submit().get()
            }catch (e: Exception){
                e.printStackTrace()
            }
            return file
        }

        /**
         * 根据url获取图片缓存
         * Glide 3.x请调用此方法
         * 注意：此方法必须在子线程中进行
         *
         * @param context
         * @param url
         * @return File?
         */
        fun getCacheFileTo3x(context: Context, url: String): File? {
            var file : File? = null
            try {
                file = Glide.with(context).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get()
            }catch (e: Exception){
                e.printStackTrace()
            }
            return file
        }

    }

}