package com.rkg.mandi.presentation.widget

import android.view.View
import androidx.core.view.postDelayed
import java.util.concurrent.atomic.AtomicBoolean

typealias OnClick = (View) -> Unit

class SingleClickListener(
    private val click: OnClick,
    private val throttleMillis: Long = 400
) : View.OnClickListener {

    private val canClick = AtomicBoolean(true)

    override fun onClick(v: View?) {
        if (canClick.getAndSet(false)) {
            v?.run {
                postDelayed(throttleMillis) {
                    canClick.set(true)
                }
                click(v)
            }
        }
    }
}