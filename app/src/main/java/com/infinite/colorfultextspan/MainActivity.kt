package com.infinite.colorfultextspan

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity.javaClass.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val spans = mutableListOf<ColorfulTextSpan>()
        val content = "In Kotlin, everything is an object in the sense that we can call member functions and properties on any variable. Some of the types can have a special internal representation - for example, numbers, characters and booleans can be represented as primitive values at runtime - but to the user they look like ordinary classes. In this section we describe the basic types used in Kotlin: numbers, characters, booleans, arrays, and strings.";
        val stringBuilder = StringBuilder()
        //第一个Span
//        stringBuilder.append(" ")
        val b1 = ColorfulTextSpan.Builder(this@MainActivity)
        val s1 = b1.backgroundColor(R.color.colorPrimary)
                .texts("新")
                .textColor(android.R.color.black)
                .padding(64)
                .textSize(60f)
                .margin(0)
                .radius(20f)
                .hollowBackground()
                .borderWidth(2f)
                .build()
        spans.add(s1)
        stringBuilder.append(s1)
        //第二个Span
        val b2 = ColorfulTextSpan.Builder(this@MainActivity)
        val s2 = b2.backgroundColor(R.color.colorAccent)
                .texts("特效药")
                .textColor(android.R.color.white)
                .padding(8)
                .textSize(60f)
                .margin(64)
                .radius(10f)
                .build()
        spans.add(s2)
        stringBuilder.append(s2)

        val b3 = ColorfulTextSpan.Builder(this@MainActivity)
        val s3 = b3.backgroundColor(R.color.colorPrimaryDark)
                .texts("国外知名药品国")
                .textColor(android.R.color.white)
                .padding(8)
                .textSize(60f)
                .margin(160)
                .radius(10f)
                .build()
        spans.add(s3)
        stringBuilder.append(s3)
        stringBuilder.append(content)

        val b4 = ColorfulTextSpan.Builder(this@MainActivity)
        val s4 = b4.backgroundColor(R.color.colorAccent)
                .texts("英文文章")
                .textColor(android.R.color.black)
                .padding(80)
                .textSize(60f)
                .margin(16)
                .radius(10f)
                .build()
        spans.add(s4)

        stringBuilder.append(s4)
        val s5 = ColorfulTextSpan.Builder(this@MainActivity)
                .texts("收费")
                .textColor(android.R.color.white)
                .backgroundColor(R.color.colorPrimary)
                .padding(80)
                .textSize(60f)
                .margin(16)
                .radius(10f)
                .build()
        spans.add(s5)
        stringBuilder.append(s5)
        val spannableString = SpannableString(stringBuilder.toString())
        //循环遍历设置Span
        for (i in spans.indices) {
            var msg = spans[i].toString()
            var start = spannableString.indexOf(msg)
            var end = spannableString.indexOf(msg) + msg.length
            spannableString.setSpan(spans[i], start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            val str="$msg start=$start end=$end"
            spannableString.setSpan(CSpan(this@MainActivity, msg,str), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            Log.e(TAG, str)
        }
        tv.movementMethod = LinkMovementMethod()
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,60f)
        tv.text = spannableString
        // todo 点击事件
    }

    class CSpan(context: Context, msg: String,str:String) : ClickableSpan() {

        private var context: Context? = null
        private var msg: String? = null
        private var str: String? = null

        init {
            this.context = context
            this.msg = msg
            this.str=str
        }

        override fun onClick(widget: View?) {
            Log.e(TAG,str)
        }

    }
}
