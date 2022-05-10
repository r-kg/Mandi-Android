package com.rkg.mandi.presentation.ui.main

import com.rkg.mandi.domain.usecase.MandiUseCase
import com.rkg.mandi.presentation.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MandiUseCase
) : BaseViewModel() {
}