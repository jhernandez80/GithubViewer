package com.livehappyapps.githubviewer.utils

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.TextView
import android.widget.Toast


val Int.toDp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun TextView.setTextOrHide(text: CharSequence?) {
    if (text.isNullOrEmpty()) {
        visibility = View.GONE
    } else {
        visibility = View.VISIBLE
        setText(text)
    }
}

fun Context.toastShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT ).show()
}
