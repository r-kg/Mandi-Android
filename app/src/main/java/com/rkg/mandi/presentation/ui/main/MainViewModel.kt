package com.rkg.mandi.presentation.ui.main

import androidx.lifecycle.viewModelScope
import com.rkg.mandi.domain.model.Mandi
import com.rkg.mandi.domain.model.updatePlant
import com.rkg.mandi.domain.model.toMandiItemModel
import com.rkg.mandi.domain.usecase.MandiUseCase
import com.rkg.mandi.presentation.model.MainItemModel
import com.rkg.mandi.presentation.model.state.StateResult
import com.rkg.mandi.presentation.model.state.StateResult.*
import com.rkg.mandi.presentation.ui.BaseViewModel
import com.rkg.mandi.utils.CalendarUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MandiUseCase
) : BaseViewModel() {

    private var targetMandiId: Int? = null

    private val mutableItemModels = MutableStateFlow<StateResult<List<MainItemModel>>>(None)

    private val mandiListState = useCase.selectAll().asState(emptyList())

    val itemModels: StateFlow<StateResult<List<MainItemModel>>> = mutableItemModels


    fun setTargetMandi(id: Int) {
        targetMandiId = id
    }

    fun plant() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val mandi = getMandiById(targetMandiId) ?: return@withContext
            val updated = mandi.updatePlant(CalendarUtil.currentTimeInMillis() / 1000.0)

            useCase.update(updated)
        }
    }

    fun deleteMandi(id: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            useCase.deleteMandi(id)
        }
    }

    fun collectMandiFlow() = viewModelScope.launch {
        mandiListState.collect { domainModels ->
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

    private fun getMandiById(id: Int?): Mandi? {
        return mandiListState.value.firstOrNull { it.id == id }
    }
}