package com.strong.ui.view.fold

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatTextView
import com.strong.R

/**
 * created by YooJin.
 * date: 2021/1/11 10:27
 * desc:折叠内容
 * https://www.cnblogs.com/LiuZhen/p/14177650.html
 */
class FoldTextView(context: Context?, private val attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatTextView(context!!, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?):this(context, attrs, 0)

    constructor(context: Context):this(context, null)

    private val tag = "FoldTextView"
    private var maxLine = 0f

    /* 是否折叠，默认折叠 */
    private var isFold = true

    /* 初始化缓存标识 */
    private var flag = false
    private val ellipsizeEndText = "..."
    private val foldText = "全部"
    private val expandText = "收起"
    private var originalText: CharSequence? = null

    init {
        init()
    }

    @SuppressLint("CustomViewStyleable")
    private fun init() {
        val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.foldTextView)
        maxLine = attributes.getInt(R.styleable.foldTextView_fold_maxLines, 0).toFloat()
        attributes.recycle()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        if (TextUtils.isEmpty(text)) {
            //判断空字符，有时候会有空字符进来，但是其实内容都已正确测量，会导致获取索引时超出数组
            super.setText(text, type)
        } else {
            //一定要先调用，否则后面的getLayout().getLineCount()测量不准确
            super.setText(text, type)
            //首次加载缓存，否则会有异步加载问题，内容会闪烁
            if (!flag) {
                viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        viewTreeObserver.removeOnPreDrawListener(this)
                        flag = true
                        setContent(text!!, type!!)
                        return true
                    }
                })
            } else {
                setContent(text!!, type!!)
            }
        }
    }

    private fun setContent(`val`: CharSequence, type: BufferType) {
        originalText = `val`
        if (layout != null) {
            val line = layout.lineCount.toFloat()
            //ELLIPSIZE_END模式来处理
            if (line > maxLine) {
                val builder = SpannableStringBuilder()
                //如果折叠，截取字符，否则加载原文
                if (isFold) {
                    val end =
                        layout.getLineEnd(maxLine.toInt() - 1) - ellipsizeEndText.length - foldText.length
                    if (end > 0 && length() > end) {
                        val title = `val`.subSequence(0, end)
                        builder.append(title)
                    }
                    builder.append(ellipsizeEndText)
                    builder.append(foldText)
                    builder.setSpan(
                        FoldSpan(),
                        builder.length - foldText.length,
                        builder.length,
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                } else {
                    builder.append(`val`)
                    builder.append(expandText)
                    builder.setSpan(
                        FoldSpan(),
                        builder.length - expandText.length,
                        builder.length,
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                }
                //不设置点击不生效
                //setMovementMethod(LinkMovementMethod.getInstance());
                super.setText(builder, type)
            } else {
                super.setText(`val`, type)
            }
            Log.e(tag, "maxLine $maxLine line $line isFold $isFold text $`val`")
        } else {
            super.setText(`val`, type)
        }
    }

    /** 需要在setText前调用  */
    fun setMaxLine(line: Int) {
        maxLine = line.toFloat()
    }

    private inner class FoldSpan : ClickableSpan() {
        override fun onClick(view: View) {
            isFold = !isFold
            text = originalText
            Log.e(tag, "click $isFold text $text")
        }

        override fun updateDrawState(paint: TextPaint) {
            paint.color = Color.parseColor("#0672D6")
            // 设置下划线 true显示、false不显示
            paint.isUnderlineText = false
        }
    }

}