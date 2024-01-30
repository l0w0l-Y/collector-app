package com.kaleksandra.corecommon.ext.log

import android.util.Log

private const val DEBUG_TAG = "DEBUG_TAG"
private const val ERROR_TAG = "ERROR_TAG"
private const val INFO_TAG = "INFO_TAG"

fun <Data> debug(data: Data, tag: String? = DEBUG_TAG) {
    Log.d(tag, data.toString())
}

fun <Data> error(data: Data, tag: String? = ERROR_TAG) {
    Log.e(tag, data.toString())
}

fun <Data> info(data: Data, tag: String? = INFO_TAG) {
    Log.i(tag, data.toString())
}