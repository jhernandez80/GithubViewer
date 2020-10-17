package com.livehappyapps.githubviewer.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.livehappyapps.githubviewer.R
import com.livehappyapps.githubviewer.databinding.ViewLabelBinding


class LabelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr){

    private var binding = ViewLabelBinding.inflate(LayoutInflater.from(context), this)

    fun setText(label: String?) {
        binding.label.text = label
    }

    fun setTextColor(@ColorInt color: Int) {
        binding.label.setTextColor(color)
    }

    override fun setCardBackgroundColor(color: Int) {
        super.setCardBackgroundColor(color)
        val textColor = if (ColorUtils.calculateLuminance(color) > 0.5) {
            R.color.primary_text
        } else {
            R.color.white
        }
        setTextColor(ContextCompat.getColor(context, textColor))
    }
}