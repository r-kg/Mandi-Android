package com.rkg.mandi.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    protected fun <T> Flow<T>.asState(initialValue: T) =
        stateIn(viewModelScope, SharingStarted.Eagerly, initialValue)

    protected fun runOnIO(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch { withContext(Dispatchers.IO, block = block) }
}