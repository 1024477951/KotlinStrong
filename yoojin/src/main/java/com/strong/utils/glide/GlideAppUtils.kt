package com.strong.utils.glide

import android.widget.ImageView
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

open class GlideAppUtils {
    companion object {
        /** 加载gif图片 */
        fun loadGif(imageView: ImageView, path: String, error: Int){
            GlideApp.with(Utils.getApp())
                .asGif()
                .load(path)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .error(error)
                .into(imageView)
        }
        /** 加载圆形图片 */
        fun loadCircle(imageView: ImageView, path: String, error: Int){
            val requestOptions = RequestOptions.circleCropTransform()
            GlideApp.with(Utils.getApp())
                .load(path)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .error(error)
                .apply(requestOptions)
                .into(imageView)
        }
        /** 加载填充+圆角图片 */
        fun loadCrop(imageView: ImageView, path: String, error: Int,radius: Int,width: Int,height: Int){
            GlideApp.with(Utils.getApp())
                .load(path)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(width,height)
                .dontAnimate()
                .error(error)
                .transform(CenterCrop(), RoundedCornersTransformation(radius, 0))
                .into(imageView)
        }
        /** 加载填充+圆角图片 */
        fun loadCrop(imageView: ImageView, path: String, error: Int,radius: Int){
            GlideApp.with(Utils.getApp())
                .load(path)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .error(error)
                .transform(CenterCrop(), RoundedCornersTransformation(radius, 0))
                .into(imageView)
        }
        /** 加载列表图片 */
        fun loadCrop(imageView: ImageView, path: String, error: Int,width: Int,height: Int){
            GlideApp.with(Utils.getApp())
                .load(path)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(width,height)
                .centerCrop()
                .apply(RequestOptions().placeholder(error))
                .dontAnimate()
                .error(error)
                .into(imageView)
        }
        /** 默认加载带圆角图片 */
        fun load(imageView: ImageView, path: String, error: Int,radius: Int){
            GlideApp.with(Utils.getApp())
                .load(path)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .error(error)
                .transform(CenterInside(), RoundedCornersTransformation(radius, 0))
                .into(imageView)
        }
        /** 默认加载图片 */
        fun load(imageView: ImageView, path: String, error: Int){
            GlideApp.with(Utils.getApp())
                .load(path)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .error(error)
                .into(imageView)
        }
        /** 默认加载drawable */
        fun load(imageView: ImageView, drawable: Int, error: Int){
            GlideApp.with(Utils.getApp())
                .load(drawable)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .error(error)
                .into(imageView)
        }
    }
}