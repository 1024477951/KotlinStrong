package com.strong.ui.view.blank

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.TextUtils
import android.util.AttributeSet
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatTextView
import com.strong.R
import kotlin.math.roundToInt

/**
 * created by YooJin.
 * date: 2021/1/11 10:54
 * desc:末尾留白,设置最大行数，在最后一行末尾会留空白
 * https://www.cnblogs.com/LiuZhen/p/14137562.html
 */
class BlankTextView(context: Context?, private val attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatTextView(context!!, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?):this(context, attrs, 0)

    constructor(context: Context):this(context, null)

    private val tag = "FoldTextView"
    private var maxLine = 0f
    private var maxHalfLine = 0f

    /** 初始化缓存标识  */
    private var flag = false

    init {
        init()
    }

    @SuppressLint("CustomViewStyleable")
    private fun init() {
        val attributes: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.bannerTextView
        )
        maxLine = attributes.getInt(R.styleable.bannerTextView_banner_maxLines, 0).toFloat()
        maxHalfLine = maxLine - 0.5f
        attributes.recycle()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        if (TextUtils.isEmpty(text)) {
            super.setText(text, type)
        }
        //首次加载缓存，否则会有异步加载问题，内容会闪烁
        if (!flag) {
            viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    viewTreeObserver.removeOnPreDrawListener(this)
                    flag = true
                    setContent(text.toString(), type!!)
                    return true
                }
            })
        } else {
            setContent(text.toString(), type!!)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setContent(`val`: String, type: BufferType) {
        //得到文本内容的总长度
        val textWidth = paint.measureText(`val`)
        //测量实际view的宽度
        val width = measuredWidth
        if (width > 0) {
            //计算内容实际行数，自带其实有getCountLine方法，但是改方法有bug，末尾如果是空格，如果刚好快到指定行数了，然后末尾加入空格，此时改方法不起作用
            val line = textWidth / width
            //快两行的话按ELLIPSIZE_END模式来处理
            if (line > maxHalfLine && line < maxLine) {
                val num = (width / textSize).roundToInt()
                val end = (num * maxHalfLine - 1).toInt()
                val title = `val`.substring(0, end)
                super.setText("$title...", type)
            } else  //不要让字数刚好两行，这样会遮住末尾的标题样式
                if (line > maxLine) {
                    //一行可以容纳多少个字
                    val num = (width / textSize).roundToInt()
                    //在最大行的末尾减去6个字符
                    val end = (num * maxLine - 6).toInt()
                    val title = `val`.substring(0, end)
                    super.setText("$title...", type)
                } else {
                    super.setText(`val`, type)
                }
            //Log.e("===", "measureText textWidth " + textWidth + " width " + width + " line " + line + " \nval "+val);
        } else {
            super.setText(`val`, type)
        }
    }

    /** 需要在setText前调用  */
    fun setMaxLine(line: Int) {
        maxLine = line.toFloat()
        //maxLine - 0.5f(半行)
        maxHalfLine = maxLine - 0.6f
    }
}