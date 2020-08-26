package com.dev.railian.mvvmtemplate.extensions

import android.content.res.Resources

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Float.toPx(): Float = (this * Resources.getSystem().displayMetrics.density)
fun Float.toDp(): Float = (this / Resources.getSystem().displayMetrics.density)