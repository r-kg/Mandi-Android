package com.rkg.mandi.domain.usecase

import com.rkg.mandi.data.model.toDomain
import com.rkg.mandi.data.repository.MandiRepository
import com.rkg.mandi.domain.model.Mandi
import com.rkg.mandi.domain.model.toEntity
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class MandiUseCase @Inject constructor(
    private val repository: MandiRepository
) {
    fun selectAll() = repository.selectAll().map { entities ->
        entities.map { it.toDomain() }
    }.catch { throwable ->

    }

    suspend fun selectById(id: Int) = runCatching {
        repository.selectById(id)
    }.mapCatching {
        it.firstOrNull()?.toDomain()
    }.getOrThrow()

    suspend fun updated(mandi: Mandi) = runCatching {
        repository.update(mandi.toEntity())
    }.getOrThrow()

    suspend fun insertMandi(mandi: Mandi) = runCatching {
        repository.insert(mandi.toEntity())
    }.onFailure { throwable ->
        // on failure
    }.getOrThrow()
}