package com.kaleksandra.corecommon.ext.dimensions

import android.content.res.Resources

fun Float.toDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density).toInt()
}

fun Float.toPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}