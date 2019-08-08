package com.kotlinstrong.stronglib.util.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kotlinstrong.stronglib.base.BaseApp

class GlideAppUtils {
    companion object {

        fun load(imageView: ImageView, path: String){
//            if (path.isNotEmpty()){
//                GlideApp.with(BaseApp.getContext())
//                    .load(path)
//                    .skipMemoryCache(false)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .dontAnimate()
//                    .into(imageView)
//            }
        }

        fun load(imageView: ImageView, drawable: Drawable){
//            if (drawable != null){
//                GlideApp.with(BaseApp.getContext())
//                    .load(drawable)
//                    .skipMemoryCache(false)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .dontAnimate()
//                    .into(imageView)
//            }
        }
    }
}