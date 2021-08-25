package com.strong.ui.font


import androidx.databinding.ObservableBoolean
import com.strong.R
import com.strong.ui.base.BaseViewModel
import com.strong.utils.font.FontUtils

class FontViewModel : BaseViewModel() {

    private val repository by lazy {  }

    var isNormalField: ObservableBoolean =
        ObservableBoolean(FontUtils.getFontUtils().getAppFontStyle().equals(FontUtils.NORMAL_FONT_STYLE))
    var normalResId: Int = R.mipmap.icon_setting_font_normal_check
    var unNormalResId: Int = R.mipmap.icon_setting_font_normal_un

    var isBigField: ObservableBoolean =
        ObservableBoolean(FontUtils.getFontUtils().getAppFontStyle().equals(FontUtils.BIG_FONT_STYLE))
    var bigResId: Int = R.mipmap.icon_setting_font_big_check
    var unBigResId: Int = R.mipmap.icon_setting_font_big_un

    var isLargeField: ObservableBoolean =
        ObservableBoolean(FontUtils.getFontUtils().getAppFontStyle().equals(FontUtils.LARGE_FONT_STYLE))
    var largeResId: Int = R.mipmap.icon_setting_font_large_check
    var unLargeResId: Int = R.mipmap.icon_setting_font_large_un
}