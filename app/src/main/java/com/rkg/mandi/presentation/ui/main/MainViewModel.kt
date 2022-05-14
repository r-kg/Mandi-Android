package com.rkg.mandi.presentation.ui.main

import com.rkg.mandi.domain.model.toMandiItemModel
import com.rkg.mandi.domain.usecase.MandiUseCase
import com.rkg.mandi.presentation.model.MainItemModel
import com.rkg.mandi.presentation.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MandiUseCase
) : BaseViewModel() {

    fun fetchMandis() = useCase.selectAll().map { data ->
        data.map { it.toMandiItemModel() }
    }
}