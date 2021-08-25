package com.strong.utils.font

import androidx.annotation.RawRes
import com.blankj.utilcode.util.*
import com.google.gson.reflect.TypeToken
import com.strong.R

/**
 * created by YooJin.
 * date: 2021/8/24 14:35
 * desc:老年模式
 */
class FontUtils {

    companion object {
        private const val TAG = "FontUtils"
        private const val KEY_APP_FONT = "key_app_font"
        //标准字体
        const val NORMAL_FONT_STYLE = "normal_text_size"
        //大号字体
        const val BIG_FONT_STYLE = "big_text_size"
        //特大字体
        const val LARGE_FONT_STYLE = "large_text_size"

        private val instance by lazy { FontUtils() }
        fun getFontUtils(): FontUtils = instance
    }

    /** 字体样式类型  */
    fun getAppFontStyle(): String {
        return SPUtils.getInstance().getString(KEY_APP_FONT, NORMAL_FONT_STYLE)
    }

    fun saveAppFontStyle(appFontStyle: String?) {
        SPUtils.getInstance().put(KEY_APP_FONT, appFontStyle)
    }

    /** 获取模型  */
    fun getFontVo(fontType: String?): FontBean? {
        val fontStyle: String = getAppFontStyle()
        val fontVoList: List<FontBean>? = getRawFileList(fontStyle)
        return if (CollectionUtils.isNotEmpty(fontVoList)) {
            // LogUtils.i(TAG, "getFontVo-- fontVo:" + GsonUtils.toJson(fontVo));
            getFontByType(fontVoList, fontType)
        } else null
    }
    /**
     * 解析模型
     * @param fontType 具体字号类型
     * */
    private fun getFontByType(fontVoList: List<FontBean>?, fontType: String?): FontBean? {
        return CollectionUtils.find(
            fontVoList
        ) {
            it != null && StringUtils.equals(it.fontType, fontType)
        }
    }
    /** 字体模型  */
    private fun getRawFileList(fontStyle: String?): List<FontBean>? {
        return when {
            StringUtils.equals(NORMAL_FONT_STYLE, fontStyle) -> {
                getFontListByRaw(R.raw.font_normal)
            }
            StringUtils.equals(BIG_FONT_STYLE, fontStyle) -> {
                getFontListByRaw(R.raw.font_big)
            }
            StringUtils.equals(LARGE_FONT_STYLE, fontStyle) -> {
                getFontListByRaw(R.raw.font_large)
            }
            else -> getFontListByRaw(R.raw.font_normal)
        }
    }
    /** 读取本地模型 路径 /res/raw/resId */
    private fun getFontListByRaw(@RawRes resId: Int): List<FontBean>? {
        return GsonUtils.fromJson<List<FontBean>>(
            ResourceUtils.readRaw2String(resId),
            object : TypeToken<List<FontBean>>() {}.type
        )
    }

}