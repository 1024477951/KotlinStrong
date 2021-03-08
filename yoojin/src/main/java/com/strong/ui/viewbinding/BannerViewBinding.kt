package com.strong.ui.viewbinding

import androidx.databinding.BindingAdapter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.strong.R
import com.strong.ui.home.adapter.HomeBannerAdapter
import com.strong.ui.home.bean.BannerBean
import com.strong.ui.splash.adapter.SplashBannerAdapter
import com.strong.ui.splash.bean.SplashBean
import com.youth.banner.Banner
import com.youth.banner.indicator.RectangleIndicator

/**  banner 轮播 */
@BindingAdapter("setHomeBanner")
fun Banner<BannerBean, HomeBannerAdapter>.setHomeBanner(list: MutableList<BannerBean>?){
    if (list != null) {
        //LogUtils.e("BannerViewBinding","banner count "+list.size)
        setAdapter(HomeBannerAdapter(list))
            //.addBannerLifecycleObserver()//添加生命周期观察者
            .setIndicator(RectangleIndicator(context))//设置指示器
            .setIndicatorSelectedWidth(SizeUtils.dp2px(8F))
            .setIndicatorNormalWidth(SizeUtils.dp2px(3F))
            .setIndicatorNormalColorRes(R.color.bg_color_banner_indicator)
            .setIndicatorSelectedColorRes(R.color.bg_color_banner_indicator)
            .setLoopTime(2000)
            .setOnBannerListener { data, position ->
                {
                    ToastUtils.showShort(data.toString())
                }
            }
    }
}

@BindingAdapter("setSplashBanner")
fun Banner<SplashBean.SplashData, SplashBannerAdapter>.setSplashBanner(list: MutableList<SplashBean.SplashData>?){
    if (list != null) {
        setAdapter(SplashBannerAdapter(list))
            .setIndicator(RectangleIndicator(context))//设置指示器
            .setIndicatorSelectedWidth(SizeUtils.dp2px(8F))
            .setIndicatorNormalWidth(SizeUtils.dp2px(3F))
            .setIndicatorNormalColorRes(R.color.bg_color_banner_indicator)
            .setIndicatorSelectedColorRes(R.color.bg_color_banner_indicator)
            .setLoopTime(1500)
            .setOnBannerListener { data, position ->
                {
                    ToastUtils.showShort(list[position].title)
                }
            }
    }
}