package com.strong.viewbinding

import android.text.TextUtils
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.strong.utils.const.LiveEventBusKey
import com.strong.utils.font.FontBean
import com.strong.utils.font.FontUtils


/**  EditText 视图绑定 */

@BindingAdapter(value = ["bindFontType"], requireAll = false)
fun EditText.setBindingFontStyle(fontType: String?) {
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
