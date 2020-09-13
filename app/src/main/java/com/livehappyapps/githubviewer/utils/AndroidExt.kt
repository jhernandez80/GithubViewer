package com.livehappyapps.githubviewer.utils

import android.content.res.Resources
import android.view.View
import android.widget.TextView


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
