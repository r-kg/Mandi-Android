package com.rkg.mandi.presentation.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.rkg.mandi.R
import com.rkg.mandi.presentation.widget.OnClick
import com.rkg.mandi.presentation.widget.SingleClickListener

fun View.setOnSingleClickListener(onClick: OnClick?) {
    onClick?.let {
        setOnClickListener(SingleClickListener(it))
    } ?: setOnClickListener(null)
}

fun View.slideInAnimation(endCallback: (() -> Unit)? = null) {
    AnimationUtils.loadAnimation(context, R.anim.slide_in).apply {
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                endCallback?.invoke()
            }
        })
    }.run {
        startAnimation(this)
    }
}
