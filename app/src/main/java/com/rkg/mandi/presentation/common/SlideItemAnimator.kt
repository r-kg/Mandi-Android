package com.rkg.mandi.presentation.common

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.rkg.mandi.presentation.utils.slideInAnimation

class SlideItemAnimator : DefaultItemAnimator() {

    var usesAnimation = false

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        return if (usesAnimation) {
            holder?.itemView?.slideInAnimation {
                dispatchAddFinished(holder)
            }
            true
        } else {
            dispatchAddFinished(holder)
            true
        }
    }
}
