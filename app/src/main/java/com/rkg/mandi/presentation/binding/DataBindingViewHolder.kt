package com.rkg.mandi.presentation.binding

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import com.rkg.mandi.BR

abstract class DataBindingViewHolder<T>(internal val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root), LayoutContainer {

    override val containerView: View?
        get() = binding.root

    fun bind(item: T, presenter: DataBindingPresenter? = null) {
        presenter?.let {
            //binding.setVariable(BR.presenter, it)
        }
        bindItem(item)
        binding.executePendingBindings()
    }

    abstract fun bindItem(item: T)
}