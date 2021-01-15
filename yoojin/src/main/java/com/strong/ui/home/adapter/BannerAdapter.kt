package com.strong.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.strong.R
import com.strong.ui.home.bean.BannerBean
import com.strong.utils.glide.GlideAppUtils
import com.youth.banner.adapter.BannerAdapter

/**
 * created by YooJin.
 * date: 2021/1/14 18:08
 * desc:
 */
class BannerAdapter(datas: MutableList<BannerBean>?) : BannerAdapter<BannerBean, ImageHolder>(datas) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
        val imageView = ImageView(parent!!.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = params
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        return ImageHolder(imageView)
    }

    override fun onBindView(holder: ImageHolder, data: BannerBean, position: Int, size: Int) {
        GlideAppUtils.load(holder.imageView,data.url, R.mipmap.icon_def_empty)
    }
}

class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
    var imageView: ImageView = view as ImageView
}