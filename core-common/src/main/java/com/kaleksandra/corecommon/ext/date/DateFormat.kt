package com.kaleksandra.corecommon.ext.date

import java.text.SimpleDateFormat
import java.util.Locale

enum class DateFormat(private val pattern: String) {
    DEFAULT_DATE_FORMAT("yyyy-MM-dd"),
    IMAGE_DATE_TIME_FORMAT("yyyyMMdd_HHmmss");

    fun toDateFormat(locale: Locale = Locale.getDefault(), value: Any) =
        pattern.toDateFormat(locale, value)

    private fun String.toDateFormat(locale: Locale, value: Any) = runCatching {
        SimpleDateFormat(this, locale).format(value)
    }.getOrNull()
}