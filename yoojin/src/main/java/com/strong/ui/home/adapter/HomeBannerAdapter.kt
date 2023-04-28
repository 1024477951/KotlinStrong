package com.strong.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils
import com.strong.R
import com.strong.ui.home.bean.BannerBean
import com.strong.baselib.utils.glide.GlideAppUtils
import com.youth.banner.adapter.BannerAdapter

/**
 * created by YooJin.
 * date: 2021/1/14 18:08
 */
class HomeBannerAdapter(list: MutableList<BannerBean.BannerData>?) :
    BannerAdapter<BannerBean.BannerData, ImageHolder>(list) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent!!.context)
            .inflate(R.layout.layout_home_banner, parent, false);
        return ImageHolder(view)
    }

    override fun onBindView(
        holder: ImageHolder,
        data: BannerBean.BannerData,
        position: Int,
        size: Int
    ) {
        GlideAppUtils.loadCrop(
            holder.imageView,
            data.sourceUrl!!,
            R.mipmap.icon_def_empty,
            SizeUtils.dp2px(8f)
        )
    }
}

class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
    var imageView: ImageView = view.findViewById(R.id.iv_img)
}