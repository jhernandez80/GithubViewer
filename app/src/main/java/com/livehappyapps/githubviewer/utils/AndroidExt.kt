package com.livehappyapps.githubviewer.utils

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController


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
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Toolbar.setupNavControllerWithTitle(toolbarTitle: String) {
    val navController = findNavController()
    val appBarConfiguration = AppBarConfiguration(navController.graph)

    title = toolbarTitle
    setupWithNavController(navController, appBarConfiguration)
}