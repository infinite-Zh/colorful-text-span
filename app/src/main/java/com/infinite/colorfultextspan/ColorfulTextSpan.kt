package com.infinite.colorfultextspan

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.text.style.ReplacementSpan
import android.util.Log

/**
 * Created by infinite on 2018/1/9.
 */

class ColorfulTextSpan private constructor(context: Context, builder: Builder) : ReplacementSpan() {

    companion object {
        val TAG = ColorfulTextSpan::class.simpleName
    }

    private val mTextPaint: Paint
    private val mBgPaint: Paint
    private var mUnderLinePaint: Paint? = null
    private var mPadding = 0
    private var mWideh = 0
    private var mText: String
    private var margin: Int = 0
    private val mRadius: Float
    private val mBuilder: Builder = builder
    private var mTextLength = 0

    init {
        mBgPaint = Paint().apply {
            if (builder.backgroundColorResId != 0) {
                color = ContextCompat.getColor(context, builder.backgroundColorResId)
            }
            if (builder.solid) {
                style = Paint.Style.FILL
            } else {
                style = Paint.Style.STROKE
                strokeWidth = builder.borderWidth
            }
        }

        mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = ContextCompat.getColor(context, builder.textColorResId)
            textSize = builder.textSize
            textAlign = Paint.Align.CENTER
        }

        mPadding = builder.padding
        mText = builder.texts[0]
        margin = builder.margin
        mRadius = builder.radius

        if (builder.underLineColorResId != 0) {
            mUnderLinePaint = Paint().apply {
                color = ContextCompat.getColor(context, builder.underLineColorResId)
                style = Paint.Style.FILL
                strokeWidth = 5f
            }
        }
    }

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        val rect = Rect()
        mTextPaint.getTextBounds(mText, 0, mText.length, rect)
        mTextLength = rect.right
        // span的宽度等于文字宽度加左右内边距、外边距
        mWideh = rect.width() + (mPadding + margin) * 2
        if (!mBuilder.solid) mWideh += 2 * mBuilder.borderWidth.toInt()

        return mWideh
    }

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val fm: Paint.FontMetrics = paint.fontMetrics
        val textHeight = fm.descent - fm.ascent
        var left = x + margin
        val t = y + fm.ascent//计算top时，忽略padding，bottom同理
        val right = left + mTextLength + 2 * mPadding
        val b = t + textHeight
        val bgRect = RectF(left, t, right, b)
        Log.e(TAG, "$mText:$left:$right")

        canvas.drawRoundRect(bgRect, mRadius, mRadius, mBgPaint)
        val fontMetrics = mTextPaint.fontMetrics

        canvas.drawText(mText, 0, mText.length, (left + right) / 2, y.toFloat(), mTextPaint)

        if (mUnderLinePaint != null) {
            canvas.drawLine(left, b, right, b, mUnderLinePaint)
        }
    }

    override fun toString(): String {
        return mText
    }

    class Builder(private val mCtx: Context) {
        var textColorResId: Int = android.R.color.black
        var textSize: Float = 50f
        var texts: MutableList<String> = mutableListOf()
        var padding = 0
        var backgroundColorResId: Int = 0
        var margin: Int = 0
        var radius: Float = 0f
        var solid: Boolean = true
        var borderWidth: Float = 1f
        var underLineColorResId: Int = 0

        /**
         * 文字颜色
         * */
        fun textColor(colorResId: Int): Builder {
            textColorResId = colorResId
            return this
        }

        /**
         * 背景色
         * 当调用了{@link hollowBackground()}设置为空心时，作为边框颜色
         * */
        fun backgroundColor(backgroundColorResId: Int): Builder {
            this.backgroundColorResId = backgroundColorResId
            return this
        }

        /**
         * 内边距
         * */
        fun padding(padding: Int): Builder {
            this.padding = padding
            return this
        }

        /**
         * 边距
         * */
        fun margin(margin: Int): Builder {
            this.margin = margin
            return this
        }

        /**
         * 背景圆角
         * */
        fun radius(radius: Float): Builder {
            this.radius = radius
            return this
        }

        /**
         * 设置背景为空心的
         * */
        fun hollowBackground(): Builder {
            solid = false
            return this
        }

        /**
         * 边框宽度
         * */
        fun borderWidth(borderWidth: Float): Builder {
            this.borderWidth = borderWidth
            return this
        }

        /**
         * text size in pixel
         *
         * @param textSize 文字大小
         * @return
         */
        fun textSize(textSize: Float): Builder {
            this.textSize = textSize
            return this
        }

        /**
         * 设置文字
         * */
        fun texts(texts: String): Builder {
            if (texts.isNotEmpty()) {
                this.texts.add(texts)
            }
            return this
        }

        fun underLineColor(resId: Int): Builder {
            this.underLineColorResId = resId
            return this
        }

        fun build(): ColorfulTextSpan {
            return ColorfulTextSpan(mCtx, this)
        }
    }
}
