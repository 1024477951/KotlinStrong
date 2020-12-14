package com.kotlinstrong.view.bounce

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import androidx.appcompat.widget.AppCompatCheckBox

/**
 * 首页菜单按钮
 */
open class BounceView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatCheckBox(context!!, attrs, defStyleAttr) {

    init {
        init()
    }

    constructor(context:Context,attrs: AttributeSet?):this(context,attrs,0)

    constructor(context:Context):this(context,null)

    private fun init() {
        setOnCheckedChangeListener { _, b ->
            if (b)
                startAnimation()
        }
    }

    fun startAnimation() {
        val lp = layoutParams as ViewGroup.MarginLayoutParams
        val topMargin = lp.topMargin
        val anim = ValueAnimator.ofInt(-10, 0 + topMargin, 6, 0 + topMargin)

        anim.addUpdateListener { animation ->
            val curValue = animation.animatedValue as Int
            layout(
                left,
                curValue + (parent as ViewGroup).paddingTop,
                right,
                curValue + height + (parent as ViewGroup).paddingBottom
            )
        }
        anim.interpolator = BounceInterpolator()//反弹
        anim.duration = 500
        anim.start()
    }
}