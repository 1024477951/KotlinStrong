package com.strong.ui.splash.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.strong.R
import com.strong.ui.splash.bean.SplashBean
import com.strong.utils.glide.GlideAppUtils
import com.youth.banner.adapter.BannerAdapter

/**
 * created by YooJin.
 * date: 2021/1/14 18:08
 */
class SplashBannerAdapter(datas: MutableList<SplashBean.SplashData>?) : BannerAdapter<SplashBean.SplashData, ImageHolder>(datas) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.layout_splash_banner,parent,false);
        return ImageHolder(view)
    }

    override fun onBindView(holder: ImageHolder, data: SplashBean.SplashData, position: Int, size: Int) {
        GlideAppUtils.load(holder.imageView,data.sourceUrl!!, R.mipmap.icon_def_empty)
    }
}

class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
    var imageView: ImageView = view.findViewById(R.id.iv_img)
}