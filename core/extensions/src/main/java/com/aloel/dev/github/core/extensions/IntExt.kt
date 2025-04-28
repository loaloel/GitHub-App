package com.aloel.dev.github.core.extensions

import android.content.Context
import android.util.TypedValue

/**
 * Created by Alul on 27/04/2025.
 * Senior Android Developer
 */

fun Int.dpToPixel(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}