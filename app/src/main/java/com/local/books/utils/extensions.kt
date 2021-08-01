package com.local.books.utils

import android.content.Context
import android.util.Log

fun String.fromLastDot(): String {
    return this.substring(this.lastIndexOf('.') + 1)
}

fun String.log(context: Context, TAG: String = "", level: Char = 'e') {
    val tagContext = context::class.java.toString().fromLastDot()
    when (level) {
        'e' -> Log.e("$tagContext $TAG", this)
        'd' -> Log.e("$tagContext $TAG", this)
    }
}

fun String.log(context: String, TAG: String = "", level: Char = 'e') {
    val tagContext = context.fromLastDot()
    when (level) {
        'e' -> Log.e("$tagContext $TAG", this)
        'd' -> Log.e("$tagContext $TAG", this)
    }
}