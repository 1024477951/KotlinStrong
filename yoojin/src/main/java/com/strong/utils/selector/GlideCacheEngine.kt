package com.strong.utils.selector

import android.content.Context
import com.luck.picture.lib.engine.CacheResourcesEngine
import java.io.File

/**
 * created by YooJin.
 * date: 2021/3/2 16:15
 * @describe：GlideCacheEngine
 */
class GlideCacheEngine : CacheResourcesEngine {

    companion object{

        private final const val GLIDE_VERSION = 4

        private val instance by lazy { GlideCacheEngine() }
        fun createCacheEngine(): GlideCacheEngine = instance
    }

    override fun onCachePath(context: Context, url: String): String {
        val cacheFile: File? = if (GLIDE_VERSION >= 4) {
            // Glide 4.x
            ImageCacheUtils.getCacheFileTo4x(context, url)
        } else {
            // Glide 3.x
            ImageCacheUtils.getCacheFileTo3x(context, url)
        }
        return if (cacheFile != null) {
            cacheFile.absolutePath
        } else ""
    }

}