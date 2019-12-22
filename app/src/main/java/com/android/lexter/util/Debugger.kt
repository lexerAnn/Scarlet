package com.android.lexter.util

import android.content.Context
import android.view.LayoutInflater
import java.text.SimpleDateFormat
import java.util.*

val Context.layoutInflater get() = LayoutInflater.from(this)
fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return format.format(date)
}
fun debugger(msg:Any?)= println("Debugigng =>${msg.toString()}")