package com.rkg.mandi.domain.usecase

import com.rkg.mandi.data.repository.MandiRepository
import com.rkg.mandi.domain.model.Mandi
import com.rkg.mandi.domain.model.toEntity
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MandiUseCase @Inject constructor(
    private val repository: MandiRepository
) {
    suspend fun insertMandi(mandi: Mandi) = runCatching {
        repository.insert(mandi.toEntity())
    }.onFailure { throwable ->
        // on failure
    }.getOrThrow()
}