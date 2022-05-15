package com.rkg.mandi.presentation.ui.new_mandi

import androidx.lifecycle.viewModelScope
import com.rkg.mandi.presentation.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NewMandiViewModel @Inject constructor() : BaseViewModel() {

    private val _title = MutableStateFlow("")
    private val _description = MutableStateFlow("")

    val title: StateFlow<String> = _title
    val description = _description

    val isEditDone = title.combine(description) { t, d ->
        t.isNotBlank() && d.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)


    fun setTitle(value: String) {
        _title.value = value
    }

    fun setDescription(value: String) {
        _description.value = value
    }
}