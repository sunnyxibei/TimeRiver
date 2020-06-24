package com.sunnyxibei.timeriver.extension

import android.content.Context

fun Context.isNavBarOnBottom(): Boolean {
    val dm = resources.displayMetrics
    val canMove = dm.widthPixels != dm.heightPixels &&
            resources.configuration.smallestScreenWidthDp < 600
    return !canMove || dm.widthPixels < dm.heightPixels
}
