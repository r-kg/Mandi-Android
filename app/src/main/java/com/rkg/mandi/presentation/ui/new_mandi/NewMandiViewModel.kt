package com.rkg.mandi.presentation.ui.new_mandi

import androidx.lifecycle.viewModelScope
import com.rkg.mandi.domain.model.Mandi
import com.rkg.mandi.domain.usecase.MandiUseCase
import com.rkg.mandi.presentation.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewMandiViewModel @Inject constructor(
    private val useCase: MandiUseCase
) : BaseViewModel() {

    private val _title = MutableStateFlow("")
    private val _description = MutableStateFlow("")
    private val _isLoading = MutableStateFlow(false)

    val title: StateFlow<String> = _title
    val description = _description
    val isLoading = _isLoading

    val isEditDone = title.combine(description) { t, d ->
        t.isNotBlank() && d.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun addMandi() = viewModelScope.launch {
        if (_isLoading.value) return@launch

        _isLoading.value = true
        useCase.insertMandi(
            Mandi(title = title.value, description = description.value)
        )
    }

    fun setTitle(value: String) {
        _title.value = value
    }

    fun setDescription(value: String) {
        _description.value = value
    }
}