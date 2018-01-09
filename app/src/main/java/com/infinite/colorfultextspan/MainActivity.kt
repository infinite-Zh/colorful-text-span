package com.infinite.colorfultextspan

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val spans = mutableListOf<ColorfulTextSpan>()
        val content = "Android是一种基于Linux的自由及开放源代码的操作系统，主要使用于移动设备，如智能手机和平板电脑，由Google公司和开放手机联盟领导及开发。尚未有统一中文名称，中国大陆地区较多人使用“安卓”或“安致”。";
        val stringBuilder = StringBuilder()
        //第一个Span
//        stringBuilder.append(" ")
        val b1 = ColorfulTextSpan.Builder(this@MainActivity)
        val s1 = b1.backgroundColor(R.color.colorPrimary)
                .texts("新")
                .textColor(android.R.color.white)
                .padding(8)
                .textSize(60f)
                .margin(16)
                .radius(20f)
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
                .margin(16)
                .radius(10f)
                .build()
        spans.add(s2)
        stringBuilder.append(s2)
        stringBuilder.append(content)
        val spannableString = SpannableString(stringBuilder.toString())
        //循环遍历设置Span
        for (i in spans.indices) {
            spannableString.setSpan(spans[i], i, i + spans[i].toString().length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        tv.text = spannableString
    }
}
