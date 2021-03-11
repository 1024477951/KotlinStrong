package com.strong.utils.selector

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.language.LanguageConfig
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.luck.picture.lib.style.PictureCropParameterStyle
import com.luck.picture.lib.style.PictureParameterStyle
import com.luck.picture.lib.tools.ScreenUtils
import com.strong.R


/**
 * created by YooJin.
 * date: 2021/3/1 16:50
 * desc:图片选择器
 */
class PictureSelectorUtils {

    companion object{

        fun openPicture(activity: AppCompatActivity,listener: OnResultCallbackListener<LocalMedia>,localMedia: List<LocalMedia>?){
            open(PictureSelector.create(activity),listener,localMedia)
        }

        fun openPicture(activity: Fragment, listener: OnResultCallbackListener<LocalMedia>, localMedia: List<LocalMedia>?){
            open(PictureSelector.create(activity),listener,localMedia)
        }

        private fun open(selector: PictureSelector, listener: OnResultCallbackListener<LocalMedia>, localMedia: List<LocalMedia>?){
            selector.openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style) // xml设置主题
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .setLanguage(LanguageConfig.CHINESE)// 设置语言，默认中文
                .maxSelectNum(9)// 最大图片选择数量
                .maxVideoSelectNum(1) // 视频最大选择数量，如果没有单独设置的需求则可以不设置，同用maxSelectNum字段
                .imageSpanCount(4)// 每行显示个数
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .selectionData(localMedia)// 是否传入已选图片
                .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                .isWeChatStyle(true)// 是否开启微信图片选择风格
                .isWithVideoImage(false)// 图片和视频是否可以同选
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .isGif(true)// 是否显示gif图片
                .isCompress(true)// 是否压缩
                .isCamera(false)// 是否显示拍照按钮
                .isEnableCrop(false)// 是否裁剪
                .isPreviewImage(true)// 是否可预览图片
                .isPreviewVideo(true)// 是否可预览视频
                .isEnablePreviewAudio(true) // 是否可播放音频
                .forResult(listener)
        }


        private fun getWeChatStyle(context: Context): PictureParameterStyle {
            // 相册主题
            val mPictureParameterStyle = PictureParameterStyle()
            // 是否改变状态栏字体颜色(黑白切换)
            mPictureParameterStyle.isChangeStatusBarFontColor = false
            // 是否开启右下角已完成(0/9)风格
            mPictureParameterStyle.isOpenCompletedNumStyle = false
            // 是否开启类似QQ相册带数字选择风格
            mPictureParameterStyle.isOpenCheckNumStyle = true
            // 状态栏背景色
            mPictureParameterStyle.pictureStatusBarColor = Color.parseColor("#393a3e")
            // 相册列表标题栏背景色
            mPictureParameterStyle.pictureTitleBarBackgroundColor = Color.parseColor("#393a3e")
            // 相册父容器背景色
            mPictureParameterStyle.pictureContainerBackgroundColor =
                ContextCompat.getColor(context, R.color.bg_color_212121)
            // 相册列表标题栏右侧上拉箭头
            mPictureParameterStyle.pictureTitleUpResId = R.drawable.picture_icon_wechat_up
            // 相册列表标题栏右侧下拉箭头
            mPictureParameterStyle.pictureTitleDownResId = R.drawable.picture_icon_wechat_down
            // 相册文件夹列表选中圆点
            mPictureParameterStyle.pictureFolderCheckedDotStyle = R.drawable.picture_orange_oval
            // 相册返回箭头
            mPictureParameterStyle.pictureLeftBackIcon = R.drawable.picture_icon_close
            // 标题栏字体颜色
            mPictureParameterStyle.pictureTitleTextColor =
                ContextCompat.getColor(context, R.color.picture_color_white)
            // 相册右侧按钮字体颜色  废弃 改用.pictureRightDefaultTextColor和.pictureRightDefaultTextColor
            mPictureParameterStyle.pictureCancelTextColor =
                ContextCompat.getColor(context, R.color.picture_color_53575e)
            // 相册右侧按钮字体默认颜色
            mPictureParameterStyle.pictureRightDefaultTextColor =
                ContextCompat.getColor(context, R.color.picture_color_53575e)
            // 相册右侧按可点击字体颜色,只针对isWeChatStyle 为true时有效果
            mPictureParameterStyle.pictureRightSelectedTextColor =
                ContextCompat.getColor(context, R.color.picture_color_white)
            // 相册右侧按钮背景样式,只针对isWeChatStyle 为true时有效果
            mPictureParameterStyle.pictureUnCompleteBackgroundStyle =
                R.drawable.picture_send_button_default_bg
            // 相册右侧按钮可点击背景样式,只针对isWeChatStyle 为true时有效果
            mPictureParameterStyle.pictureCompleteBackgroundStyle =
                R.drawable.picture_send_button_bg
            // 选择相册目录背景样式
            mPictureParameterStyle.pictureAlbumStyle = R.drawable.picture_new_item_select_bg
            // 相册列表勾选图片样式
            mPictureParameterStyle.pictureCheckedStyle = R.drawable.picture_wechat_num_selector
            // 相册标题背景样式 ,只针对isWeChatStyle 为true时有效果
            mPictureParameterStyle.pictureWeChatTitleBackgroundStyle = R.drawable.picture_album_bg
            // 微信样式 预览右下角样式 ,只针对isWeChatStyle 为true时有效果
            mPictureParameterStyle.pictureWeChatChooseStyle = R.drawable.picture_wechat_select_cb
            // 相册返回箭头 ,只针对isWeChatStyle 为true时有效果
            mPictureParameterStyle.pictureWeChatLeftBackStyle = R.drawable.picture_icon_back
            // 相册列表底部背景色
            mPictureParameterStyle.pictureBottomBgColor =
                ContextCompat.getColor(context, R.color.picture_color_grey)
            // 已选数量圆点背景样式
            mPictureParameterStyle.pictureCheckNumBgStyle = R.drawable.picture_num_oval
            // 相册列表底下预览文字色值(预览按钮可点击时的色值)
            mPictureParameterStyle.picturePreviewTextColor =
                ContextCompat.getColor(context, R.color.picture_color_white)
            // 相册列表底下不可预览文字色值(预览按钮不可点击时的色值)
            mPictureParameterStyle.pictureUnPreviewTextColor =
                ContextCompat.getColor(context, R.color.picture_color_9b)
            // 相册列表已完成色值(已完成 可点击色值)
            mPictureParameterStyle.pictureCompleteTextColor =
                ContextCompat.getColor(context, R.color.picture_color_white)
            // 相册列表未完成色值(请选择 不可点击色值)
            mPictureParameterStyle.pictureUnCompleteTextColor =
                ContextCompat.getColor(context, R.color.picture_color_53575e)
            // 预览界面底部背景色
            mPictureParameterStyle.picturePreviewBottomBgColor =
                ContextCompat.getColor(context, R.color.picture_color_half_grey)
            // 外部预览界面删除按钮样式
            mPictureParameterStyle.pictureExternalPreviewDeleteStyle =
                R.drawable.picture_icon_delete
            // 原图按钮勾选样式  需设置.isOriginalImageControl(true); 才有效
            mPictureParameterStyle.pictureOriginalControlStyle =
                R.drawable.picture_original_wechat_checkbox
            // 原图文字颜色 需设置.isOriginalImageControl(true); 才有效
            mPictureParameterStyle.pictureOriginalFontColor =
                ContextCompat.getColor(context, R.color.white)
            // 外部预览界面是否显示删除按钮
            mPictureParameterStyle.pictureExternalPreviewGonePreviewDelete = true
            // 设置NavBar Color SDK Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP有效
            mPictureParameterStyle.pictureNavBarColor = Color.parseColor("#393a3e")
            // 标题栏高度
            mPictureParameterStyle.pictureTitleBarHeight = ScreenUtils.dip2px(context, 48f)
            // 标题栏右侧按钮方向箭头left Padding
            mPictureParameterStyle.pictureTitleRightArrowLeftPadding =
                ScreenUtils.dip2px(context, 3f)

            // 完成文案是否采用(%1$d/%2$d)的字符串，只允许两个占位符哟
//        mPictureParameterStyle.isCompleteReplaceNum = true;
            // 自定义相册右侧文本内容设置
//        mPictureParameterStyle.pictureUnCompleteText = getString(R.string.app_wechat_send);
            //自定义相册右侧已选中时文案 支持占位符String 但只支持两个 必须isCompleteReplaceNum为true
//        mPictureParameterStyle.pictureCompleteText = getString(R.string.app_wechat_send_num);
//        // 自定义相册列表不可预览文字
//        mPictureParameterStyle.pictureUnPreviewText = "";
//        // 自定义相册列表预览文字
//        mPictureParameterStyle.picturePreviewText = "";
//        // 自定义预览页右下角选择文字文案
//        mPictureParameterStyle.pictureWeChatPreviewSelectedText = "";

//        // 自定义相册标题文字大小
//        mPictureParameterStyle.pictureTitleTextSize = 9;
//        // 自定义相册右侧文字大小
//        mPictureParameterStyle.pictureRightTextSize = 9;
//        // 自定义相册预览文字大小
//        mPictureParameterStyle.picturePreviewTextSize = 9;
//        // 自定义相册完成文字大小
//        mPictureParameterStyle.pictureCompleteTextSize = 9;
//        // 自定义原图文字大小
//        mPictureParameterStyle.pictureOriginalTextSize = 9;
//        // 自定义预览页右下角选择文字大小
//        mPictureParameterStyle.pictureWeChatPreviewSelectedTextSize = 9;
            return mPictureParameterStyle
        }

    }

}