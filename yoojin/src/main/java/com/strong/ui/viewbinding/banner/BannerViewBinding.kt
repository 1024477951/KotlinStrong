package com.strong.ui.viewbinding.banner

import androidx.databinding.BindingAdapter
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.strong.R
import com.strong.ui.home.adapter.BannerAdapter
import com.strong.ui.home.bean.BannerBean
import com.youth.banner.Banner
import com.youth.banner.indicator.RectangleIndicator

/**  banner 轮播 */
@BindingAdapter("setBanner")
fun setBanner(banner: Banner<BannerBean, BannerAdapter>, list: MutableList<BannerBean>){
    banner.setAdapter(BannerAdapter(list))
        //.addBannerLifecycleObserver()//添加生命周期观察者
        .setIndicator(RectangleIndicator(banner.context))//设置指示器
        .setIndicatorSelectedWidth(SizeUtils.dp2px(8F))
        .setIndicatorNormalWidth(SizeUtils.dp2px(3F))
        .setIndicatorNormalColorRes(R.color.bg_color_banner_indicator)
        .setIndicatorSelectedColorRes(R.color.bg_color_banner_indicator)
        .setOnBannerListener { data, position -> {
                ToastUtils.showShort(data.toString())
            }
        }
}