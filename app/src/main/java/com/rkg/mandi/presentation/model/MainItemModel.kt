package com.rkg.mandi.presentation.model

import androidx.annotation.LayoutRes
import com.rkg.mandi.R
import com.rkg.mandi.presentation.binding.MandiTapEvent
import com.rkg.mandi.presentation.binding.MandiTapEvent.*
import com.rkg.mandi.presentation.binding.SimpleDiffCallback.*

sealed class MainItemModel(@LayoutRes val layoutResId: Int) : DiffCallback {

    data class MandiItemModel(
        val id: Int,
        val title: String,
        val lastUpdated: String,
        val streakCount: String,
        val plantTap: MandiTapEvent = PlantTap(id)
    ) : MainItemModel(R.layout.main_mandi_item_model)

    override fun areItemsTheSame(other: DiffCallback): Boolean {
        return if (this is MandiItemModel && other is MandiItemModel) {
            this.id == other.id
        } else {
            super.areItemsTheSame(other)
        }
    }

    override fun areContentsTheSame(other: DiffCallback): Boolean {
        return if (this is MandiItemModel && other is MandiItemModel) {
            this.lastUpdated == other.lastUpdated
        } else {
            super.areContentsTheSame(other)
        }
    }
}