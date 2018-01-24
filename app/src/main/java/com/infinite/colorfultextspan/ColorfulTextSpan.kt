package com.infinite.colorfultextspan

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.text.style.ReplacementSpan
import android.util.Log
import java.util.*

/**
 * Created by infinite on 2018/1/9.
 */

class ColorfulTextSpan private constructor(context: Context, builder: Builder) : ReplacementSpan() {

    companion object {
        val TAG=ColorfulTextSpan::class.simpleName
    }
    private val mTextPaint: Paint
    private val mBgPaint: Paint
    private var mPadding = 0
    private var mWideh = 0
    private var mText: String
    private var margin: Int = 0
    private val mRadius:Float
    private val  mBuilder:Builder = builder

    init {
        mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mBgPaint = Paint()
        mBgPaint.color = ContextCompat.getColor(context, builder.backgroundColorResId)
        if (builder.solid){
            mBgPaint.style = Paint.Style.FILL
        }else{
            mBgPaint.style = Paint.Style.STROKE
            mBgPaint.strokeWidth=builder.borderWidth
        }
        mTextPaint.color = ContextCompat.getColor(context, builder.textColorResId)
        mTextPaint.textSize = builder.textSize
        mTextPaint.textAlign = Paint.Align.CENTER
        mPadding = builder.padding
        mText = builder.texts[0]
        margin = builder.margin
        mRadius=builder.radius
    }

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        val rect = Rect()
        mTextPaint.getTextBounds(mText, 0, mText.length, rect)
        // span的宽度等于文字宽度加左右内边距
        mWideh = rect.width() + 2 * mPadding+margin
        if (!mBuilder.solid) mWideh+=2*mBuilder.borderWidth.toInt()

        return mWideh
    }

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val fm: Paint.FontMetrics = paint.fontMetrics
        val textHeight = fm.descent - fm.ascent
        var left = x
        if (!mBuilder.solid){
            left+=2*mBuilder.borderWidth.toInt()
        }
        val t = y + fm.ascent//计算top时，忽略padding，bottom同理
        val right = left + mWideh-margin
        val b = t + textHeight  + fm.descent
        val bgRect = RectF(left, t, right, b)
        canvas.drawRoundRect(bgRect, mRadius, mRadius, mBgPaint)
        val fontMetrics = mTextPaint.fontMetrics
        val textBaseLine = y + fontMetrics.descent/2
        canvas.drawText(mText, 0, mText.length, left + (mWideh-margin) / 2.toFloat(), textBaseLine, mTextPaint)
    }

    override fun toString(): String {
        return mText
    }

    class Builder(private val mCtx: Context) {
        var textColorResId: Int = android.R.color.white
        var textSize: Float = 50f
        var texts: MutableList<String> = mutableListOf()
        var padding = 0
        var backgroundColorResId: Int = R.color.colorPrimary
        var margin: Int = 0
        var radius:Float=0f
        var solid:Boolean=true
        var borderWidth:Float=1f

        fun textColor(colorResId: Int): Builder {
            textColorResId = colorResId
            return this
        }

        fun backgroundColor(backgroundColorResId: Int): Builder {
            this.backgroundColorResId = backgroundColorResId
            return this
        }

        fun padding(padding: Int): Builder {
            this.padding = padding
            return this
        }

        fun margin(margin: Int): Builder {
            this.margin = margin
            return this
        }
        fun radius(radius:Float):Builder{
            this.radius=radius
            return this
        }

        fun hollowBackground():Builder{
            solid=false
            return this
        }

        fun borderWidth(borderWidth:Float): Builder{
            this.borderWidth=borderWidth
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

        fun texts(texts: String): Builder {
            if (texts.isNotEmpty()) {
                this.texts.add(texts)
            }
            return this
        }

        fun build(): ColorfulTextSpan {
            return ColorfulTextSpan(mCtx, this)
        }
    }
}
