package com.rkg.mandi.presentation.binding

import androidx.databinding.ViewDataBinding

class SimpleDataBindingViewHolder<T>(binding: ViewDataBinding) :
    DataBindingViewHolder<T>(binding) {

    override fun bindItem(item: T) {
        //binding.setVariable(BR.item, item)
    }
}