package com.strong.ui.font

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.strong.R
import com.strong.databinding.ActivitySettingFontBinding
import com.strong.ui.base.BaseBindActivity
import com.strong.ui.font.click.FunctionClick
import com.strong.utils.aspect.MyAnnotationOnclick
import com.strong.utils.const.LiveEventBusKey
import com.strong.utils.font.FontUtils

/**
 * 老年模式 字体设置
 */
class SettingFontActivity : BaseBindActivity<ActivitySettingFontBinding, FontViewModel>() {

    companion object{
        fun startSettingFontActivity(){
            ActivityUtils.startActivity(SettingFontActivity::class.java)
        }
    }

    override fun layoutId() = R.layout.activity_setting_font

    override fun providerVMClass() = FontViewModel::class.java

    override fun initData(bundle: Bundle?) {
        binding.model = mViewModel
        binding.click = click
    }

    /** 设置后呈现效果在首页我的tab中 */
    private val click = object : FunctionClick {
        @MyAnnotationOnclick
        override fun click(view: View) {
            when (view.id) {
                R.id.iv_normal -> {
                    mViewModel.isNormalField.set(!mViewModel.isNormalField.get())
                    mViewModel.isBigField.set(false)
                    mViewModel.isLargeField.set(false)
                    FontUtils.getFontUtils().saveAppFontStyle(FontUtils.NORMAL_FONT_STYLE)
                }
                R.id.iv_big -> {
                    mViewModel.isBigField.set(!mViewModel.isBigField.get())
                    mViewModel.isNormalField.set(false)
                    mViewModel.isLargeField.set(false)
                    FontUtils.getFontUtils().saveAppFontStyle(FontUtils.BIG_FONT_STYLE)
                }
                R.id.iv_large -> {
                    mViewModel.isLargeField.set(!mViewModel.isLargeField.get())
                    mViewModel.isBigField.set(false)
                    mViewModel.isNormalField.set(false)
                    FontUtils.getFontUtils().saveAppFontStyle(FontUtils.LARGE_FONT_STYLE)
                }
            }
            LiveEventBus
                .get(LiveEventBusKey.FONT_STYLE, Int::class.java).post(view.id)
        }

    }

}