package com.rkg.mandi.presentation.ui.main

import androidx.databinding.ViewDataBinding
import com.rkg.mandi.presentation.binding.*
import com.rkg.mandi.presentation.model.MainItemModel

class MainListAdapter(presenter: DataBindingPresenter? = null) :
    DataBindingAdapter<MainItemModel>(presenter, SimpleDiffCallback()) {

    override fun getItemViewType(position: Int): Int = getItem(position).layoutResId
    override fun createDataBindingViewHolder(binding: ViewDataBinding) =
        SimpleDataBindingViewHolder<MainItemModel>(binding)
}