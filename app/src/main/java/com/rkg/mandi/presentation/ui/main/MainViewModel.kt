package com.rkg.mandi.presentation.ui.main

import androidx.lifecycle.viewModelScope
import com.rkg.mandi.domain.model.Mandi
import com.rkg.mandi.domain.model.reset
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

    fun plant() = runOnIO {
        val mandi = getMandiById(targetMandiId) ?: return@runOnIO
        val updated = mandi.updatePlant(CalendarUtil.currentTimeInMillis() / 1000.0)

        useCase.update(updated)
    }

    fun resetMandi(id: Int) = runOnIO {
        val mandi = getMandiById(id) ?: return@runOnIO
        val reset = mandi.reset()

        useCase.update(reset)
    }

    fun deleteMandi(id: Int) = runOnIO {
        useCase.delete(id)
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