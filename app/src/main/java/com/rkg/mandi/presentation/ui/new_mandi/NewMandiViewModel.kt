package com.rkg.mandi.presentation.ui.new_mandi

import androidx.lifecycle.viewModelScope
import com.rkg.mandi.domain.model.Mandi
import com.rkg.mandi.domain.usecase.MandiUseCase
import com.rkg.mandi.presentation.ui.BaseViewModel
import com.rkg.mandi.presentation.ui.new_mandi.NewMandiViewModel.NewMandiState.*
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
    private val _state = MutableStateFlow<NewMandiState>(None)

    val title: StateFlow<String> = _title
    val description = _description
    val state = _state

    val isEditDone = title.combine(description) { t, d ->
        t.isNotBlank() && d.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun addMandi() = viewModelScope.launch {
        if (_state.value is Loading) return@launch

        _state.emit(Loading)
        runCatching {
            useCase.insert(
                Mandi(title = title.value, description = description.value)
            )
        }.onSuccess {
            _state.emit(Success)
        }.onFailure {
            _state.emit(Failed)
        }
    }

    fun setTitle(value: String) {
        _title.value = value
    }

    fun setDescription(value: String) {
        _description.value = value
    }

    sealed class NewMandiState {
        object None : NewMandiState()
        object Loading : NewMandiState()
        object Success : NewMandiState()
        object Failed : NewMandiState()
    }
}