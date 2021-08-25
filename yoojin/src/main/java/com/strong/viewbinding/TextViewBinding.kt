package com.strong.viewbinding

import android.text.TextUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.strong.utils.const.LiveEventBusKey
import com.strong.utils.font.FontBean
import com.strong.utils.font.FontUtils


/**  TextView 视图绑定 */

@BindingAdapter(value = ["bindFontType"], requireAll = false)
fun TextView.setBindingFontStyle(fontType: String?) {
    if (TextUtils.isEmpty(fontType)) {
        LogUtils.i("setBindingFontStyle", "IS NULL")
        return
    }
    //去除字体内边距
    includeFontPadding = false
    val fontVo: FontBean? = FontUtils.getFontUtils().getFontVo(fontType)
    if (fontVo != null) {
        textSize = fontVo.fontSize
    }
    val activity = ActivityUtils.getActivityByContext(context) as AppCompatActivity?
    if (activity != null) {
        LiveEventBus
            .get(LiveEventBusKey.FONT_STYLE, Int::class.java)
            .observe(activity, {
                val fontBean: FontBean? = FontUtils.getFontUtils().getFontVo(fontType)
                if (fontBean != null) {
                    textSize = fontBean.fontSize
                }
            })
    }
}

@BindingAdapter(value = ["bindFontType", "isCheck", "checkBuffSize"], requireAll = false)
fun TextView.setBindingFontStyle(fontType: String?, isCheck: Boolean, checkBuffSize: Int) {
    if (TextUtils.isEmpty(fontType)) {
        LogUtils.i("setBindingFontStyle", "IS NULL")
        return
    }
    //去除字体内边距
    includeFontPadding = false
    //选中字体
    val fontVo: FontBean? = FontUtils.getFontUtils().getFontVo(fontType)
    if (fontVo != null) {
        textSize = if (isCheck) {
            fontVo.fontSize + checkBuffSize
        } else {
            fontVo.fontSize
        }
    }
    val activity = ActivityUtils.getActivityByContext(context) as AppCompatActivity?
    if (activity != null) {
        LiveEventBus
            .get(LiveEventBusKey.FONT_STYLE, Int::class.java)
            .observe(activity, {
                val fontBean: FontBean? = FontUtils.getFontUtils().getFontVo(fontType)
                if (fontBean != null) {
                    textSize = fontBean.fontSize
                }
            })
    }
}