package com.aman.wall.paper.util

import android.content.Context
import android.os.Environment

fun getExternalDir(context: Context): String {
    val state = Environment.getExternalStorageState()
    return if (Environment.MEDIA_MOUNTED == state) {
        context.getExternalFilesDir(null)!!.absolutePath
    } else {
        context.filesDir.absolutePath
    }
}