package com.rkg.mandi.presentation.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalSpaceItemDecoration(
    private val topSpacing: Int,
    private val bottomSpacing: Int
) : RecyclerView.ItemDecoration(){

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.top = topSpacing
        outRect.bottom = bottomSpacing
    }
}