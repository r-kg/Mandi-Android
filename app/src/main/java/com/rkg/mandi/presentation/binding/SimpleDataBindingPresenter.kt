package com.rkg.mandi.presentation.binding

import android.view.View

open class SimpleDataBindingPresenter : DataBindingPresenter {
    override fun onClick(view: View, item: Any) {}
    override fun onLongClick(view: View, item: Any): Boolean = false
}