package com.kotlinstrong.stronglib.util.glide

import android.widget.ImageView

open class GlideAppUtils {
    companion object {

        fun load(imageView: ImageView, path: String,error: Int){
//            GlideApp.with(Utils.getApp())
//                .load(path)
//                .skipMemoryCache(false)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .dontAnimate()
//                .error(error)
//                .into(imageView)
        }

        fun load(imageView: ImageView, drawable: Int,error: Int){
//            GlideApp.with(Utils.getApp())
//                .load(drawable)
//                .skipMemoryCache(false)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .dontAnimate()
//                .error(error)
//                .into(imageView)
        }
    }
}