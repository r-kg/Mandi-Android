package com.rkg.mandi.presentation.utils

import android.view.View
import com.rkg.mandi.presentation.widget.OnClick
import com.rkg.mandi.presentation.widget.SingleClickListener

fun View.setOnSingleClickListener(onClick: OnClick?) {
    onClick?.let {
        setOnClickListener(SingleClickListener(it))
    } ?: setOnClickListener(null)
}