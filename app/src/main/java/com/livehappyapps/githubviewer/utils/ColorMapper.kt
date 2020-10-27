package com.livehappyapps.githubviewer.utils

import android.graphics.Color
import kotlin.random.Random


object ColorMapper {

    private val colors = mutableMapOf<String, Int>()

    fun getColorFor(value: String) = colors.getOrPut(value) {
        Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    }
}