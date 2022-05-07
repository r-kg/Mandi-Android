package com.rkg.mandi.presentation.binding

import android.view.View

interface DataBindingPresenter {
    fun onClick(view: View, item: Any)
    fun onLongClick(view: View, item: Any): Boolean
}