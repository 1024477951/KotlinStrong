package com.strong.ui.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SpanUtils
import com.strong.R
import com.strong.databinding.LoginLayoutTextTranslationXGuideBinding

/** 登录引导动画 */
class TextTranslationXGuideView(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs) {

    private var mBinding: LoginLayoutTextTranslationXGuideBinding? = null

    private var mTranslationAnimator: ValueAnimator? = null
    private var mFlickerAnimator: ValueAnimator? = null

    private val guideList = mutableListOf<TextTranslationXGuideBean>()
    private var position = 0//当前显示的引导索引

    //是否结束动画，用于等待动画结束时点击跳转
    var isFinishAnimation = false
    private val runnable = {
        isFinishAnimation = true
    }

    init {
        initView()
        initTranslationAnimation()
        initGuideRightAnimate()
    }

    private fun initView() {
        val root = LayoutInflater.from(context)
            .inflate(R.layout.login_layout_text_translation_x_guide, this, false)
        root.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(root)
        mBinding = LoginLayoutTextTranslationXGuideBinding.bind(root)
    }

    private fun initTranslationAnimation() {
        val point = -ScreenUtils.getScreenWidth().toFloat()
        mTranslationAnimator = ValueAnimator.ofFloat(0f, point)
        mTranslationAnimator?.duration = 300
        mTranslationAnimator?.interpolator = LinearInterpolator()
        mTranslationAnimator?.addUpdateListener { animation ->
            val scrollX = animation.animatedValue as Float
            translationX = scrollX
            if (scrollX <= point) {
                mTranslationAnimator?.cancel()
                alpha = 0f
                translationX = 0f
                nextGuide()
            }
        }
    }

    private fun startTranslationAnimator() {
        mTranslationAnimator?.start()
    }

    private fun initGuideRightAnimate() {
        mFlickerAnimator = ValueAnimator.ofFloat(0f, 1f)
        mFlickerAnimator?.duration = 600
        mFlickerAnimator?.interpolator = LinearInterpolator()
        mFlickerAnimator?.repeatMode = ValueAnimator.REVERSE
        mFlickerAnimator?.repeatCount = ValueAnimator.INFINITE
        mFlickerAnimator?.addUpdateListener { animation ->
            val alpha = animation.animatedValue as Float
            mBinding?.ivGuide2?.alpha = alpha
        }
    }

    private fun startGuideRightAnimator() {
        mBinding?.ivGuide2?.visibility = View.VISIBLE
        mBinding?.ivGuide2?.alpha = 0f
        mFlickerAnimator?.start()
        //需要等待勋章球正常落下到底部在跳转，这里无法获取具体掉落时间，延迟处理
        mBinding?.root?.postDelayed(runnable, 500)
    }

    /** 开始时调用 */
    fun initGuide() {
        position = 0
        if (guideList.size > 0) {
            guideList.getOrNull(position)?.let {
                setGuideContent(it)
            }
            //渐入
            alpha = 0f
            startAlphaAnimation(1500) {
                startTranslationAnimator()
            }
        }
    }

    /** 下一个引导 */
    private fun nextGuide() {
        position += 1
        //是否为最后一条数据
        val isEndGuide = position == guideList.size - 1
        //第一个图标需要先展示
        mBinding?.ivGuide1?.visibility = if (isEndGuide) View.VISIBLE else View.GONE
        guideList.getOrNull(position)?.let {
            setGuideContent(it)
            startAlphaAnimation {
                if (position < guideList.size - 1) {
                    //如果有，循环执行下一个引导
                    startTranslationAnimator()
                } else {
                    //最后一个，执行渐变闪烁动画
                    startGuideRightAnimator()
                }
            }
        }
    }

    private fun startAlphaAnimation(duration: Long = 1000L, endListener: (() -> Unit)) {
        animate().setDuration(duration).alpha(1f)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {}

                override fun onAnimationEnd(p0: Animator) {
                    endListener.invoke()
                }

                override fun onAnimationCancel(p0: Animator) {}

                override fun onAnimationRepeat(p0: Animator) {}
            })
    }

    /** 设置引导内容 */
    private fun setGuideContent(bean: TextTranslationXGuideBean) {
        mBinding?.tvContent?.text = bean.content
        val span = SpanUtils.with(mBinding?.tvContent)
            .append(bean.content)
            .setForegroundColor(resources.getColor(R.color.black, null))
        bean.bright?.let {
            span.append("\n${bean.bright}")
                .setForegroundColor(resources.getColor(bean.brightColor, null))
        }
        span.create()
    }

    /**
     * 添加单个引导文本
     * @param content 内容
     * @param bright 高亮文本
     * @param brightColor 高亮字体颜色
     * */
    fun addTextGuide(
        content: String,
        bright: String? = null,
        brightColor: Int? = null
    ): TextTranslationXGuideView {
        guideList.add(TextTranslationXGuideBean(content, bright, brightColor ?: R.color.black))
        return this
    }

    fun clear() {
        guideList.clear()
        mTranslationAnimator?.cancel()
        mTranslationAnimator = null
        mFlickerAnimator?.cancel()
        mFlickerAnimator = null
        mBinding?.root?.removeCallbacks(runnable)
    }

    data class TextTranslationXGuideBean(
        val content: String, //内容
        val bright: String?, //高亮文本
        val brightColor: Int = R.color.black //高亮字体颜色
    )

}