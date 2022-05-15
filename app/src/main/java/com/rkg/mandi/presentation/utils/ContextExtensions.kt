package com.rkg.mandi.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.activity.ComponentActivity
import com.rkg.mandi.presentation.ui.new_mandi.NewMandiActivity

inline fun <reified T : Activity> Context.startActivity(initializer: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java).apply {
        initializer()
    }

    startActivity(intent)
}

tailrec fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun Activity.startNewMandiActivity() {
    startActivity<NewMandiActivity>()
}