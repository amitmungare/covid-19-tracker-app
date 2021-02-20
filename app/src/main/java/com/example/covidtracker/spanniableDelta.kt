package com.example.covidtracker

import android.graphics.Color
import android.graphics.ColorFilter
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan

class spanniableDelta(text:String , color:String, start:Int ): SpannableString(text){

    init {
        setSpan(
            ForegroundColorSpan(Color.parseColor(color)),start,text.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}