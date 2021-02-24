package com.strong.ui.view.progress

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.SizeUtils
import com.strong.R
import kotlin.math.min

/**
 * 圆形进度
 */
open class CircleProgressView(context: Context?, private val attrs: AttributeSet?, defStyleAttr: Int) :
    View(context!!, attrs, defStyleAttr) {

    init {
        init()
    }

    constructor(context:Context,attrs: AttributeSet?):this(context,attrs,0)

    constructor(context:Context):this(context,null)

    private lateinit var bgPaint: Paint
    private lateinit var tintPaint: Paint
    private lateinit var textPaint: Paint
    /** 半径 */
    private var mRadius: Float = 0.0f
    /** 圆环宽度 */
    private var mStrokeWidth = 0.0f

    private var mWidth = 0
    private var mHeight = 0

    /** 进度 */
    private var mProgress: Float = 0.0f
    /** 最大进度值 */
    private var max: Float = 0.0f
    /** 进度区域 */
    private lateinit var mRect: RectF

    @SuppressLint("CustomViewStyleable")
    private fun init() {

        val attributes: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.circleProgress
        )
        mStrokeWidth = attributes.getDimension(R.styleable.circleProgress_circle_width, 1f)
        max = attributes.getFloat(R.styleable.circleProgress_circle_max, 100f)
        mProgress = attributes.getFloat(R.styleable.circleProgress_circle_progress, 0f)
        val bgColor = attributes.getColor(R.styleable.circleProgress_circle_color,Color.GRAY)
        val progressColor = attributes.getColor(R.styleable.circleProgress_circle_progress_color,Color.BLUE)
        val textColor = attributes.getColor(R.styleable.circleProgress_circle_text_color,Color.BLACK)
        val textSize = attributes.getDimension(R.styleable.circleProgress_circle_text_size,SizeUtils.dp2px(10f).toFloat())
        val isBold = attributes.getBoolean(R.styleable.circleProgress_circle_text_isBold,false)
        attributes.recycle()

        bgPaint = Paint()
        bgPaint.style = Paint.Style.STROKE
        bgPaint.strokeWidth = mStrokeWidth
        bgPaint.color = bgColor
        bgPaint.isAntiAlias = true

        tintPaint = Paint()
        tintPaint.style = Paint.Style.STROKE
        tintPaint.strokeWidth = mStrokeWidth
        tintPaint.color = progressColor
        tintPaint.isAntiAlias = true

        textPaint = Paint()
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = textSize
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.isFakeBoldText = isBold
        textPaint.color = textColor
        textPaint.isAntiAlias = true
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = getRealSize(widthMeasureSpec)
        mHeight = getRealSize(heightMeasureSpec)
        /** 直径 - 等宽高充满 | 当宽高不一致时，取最小的画圆 */
        val diameter = min(mWidth,mHeight)
        //半径
        mRadius = (diameter / 2).toFloat() - mStrokeWidth
        /** 进度条绘制区域 */
        val mX = (mWidth/2-diameter/2).toFloat()
        val mY = (mHeight/2-diameter/2).toFloat()
        mRect = RectF(mX + mStrokeWidth, mY + mStrokeWidth, mX + diameter - mStrokeWidth, mY + diameter - mStrokeWidth)

        setMeasuredDimension(mWidth,mHeight)
    }

    private fun getRealSize(measureSpec: Int): Int{
        val result: Int
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        result = if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            (mRadius * 2 + mStrokeWidth).toInt()
        } else {
            size
        }
        return result
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val progress = mProgress / max * 360
        //绘制圆形
        canvas!!.drawCircle((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), mRadius, bgPaint)
        //绘制进度
        canvas.drawArc(mRect, -90f, progress, false, tintPaint)
        Log.e("===","mProgress $mProgress max $max progress $progress ")
        val centerY = mHeight / 2 + mStrokeWidth / 2
        //绘制文字
        val percentage: Int = (mProgress / max * 100).toInt()
        canvas.drawText("${percentage}%", (mWidth / 2).toFloat(), centerY, textPaint)
    }

    fun setProgress(progress: Float){
        mProgress = progress
        invalidate()
    }

    fun setMax(max: Float){
        this.max = max
    }

}