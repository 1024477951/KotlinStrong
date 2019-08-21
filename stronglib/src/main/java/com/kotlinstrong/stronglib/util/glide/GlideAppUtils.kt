package com.kotlinstrong.stronglib.util.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kotlinstrong.stronglib.base.BaseApp

open class GlideAppUtils {
    companion object {

        fun load(imageView: ImageView, path: String,error: Int){
            GlideApp.with(BaseApp.getContext())
                .load(path)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .error(error)
                .into(imageView)
        }

        fun load(imageView: ImageView, drawable: Drawable,error: Int){
            GlideApp.with(BaseApp.getContext())
                .load(drawable)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .error(error)
                .into(imageView)
        }
    }
}