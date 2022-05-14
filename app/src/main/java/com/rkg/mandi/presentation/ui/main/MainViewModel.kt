package com.rkg.mandi.presentation.ui.main

import androidx.lifecycle.viewModelScope
import com.rkg.mandi.domain.model.toMandiItemModel
import com.rkg.mandi.domain.usecase.MandiUseCase
import com.rkg.mandi.presentation.model.MainItemModel
import com.rkg.mandi.presentation.model.state.StateResult
import com.rkg.mandi.presentation.model.state.StateResult.*
import com.rkg.mandi.presentation.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MandiUseCase
) : BaseViewModel() {

    private val mutableItemModels = MutableStateFlow<StateResult<List<MainItemModel>>>(None)

    val itemModels: StateFlow<StateResult<List<MainItemModel>>> = mutableItemModels


    fun fetchMandiFlow() = viewModelScope.launch {
        useCase.selectAll().collect { domainModels ->
            runCatching {
                val mandiItemModels = domainModels.map { it.toMandiItemModel() }

                mandiItemModels
            }.onSuccess {
                mutableItemModels.emit(Success(it))
            }.onFailure { e ->
                mutableItemModels.emit(Failure(e))
            }.getOrThrow()
        }
    }
}