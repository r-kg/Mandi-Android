package com.rkg.mandi.data.repository

import com.rkg.mandi.data.local.AppDatabase
import com.rkg.mandi.data.model.MandiEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MandiRepository @Inject constructor(
    private val localDataSource: AppDatabase
) {
    fun selectAll() = localDataSource.mandiDao().selectAll()

    suspend fun selectById(id: Int) = localDataSource.mandiDao().selectById(id)

    suspend fun insert(mandiEntity: MandiEntity) =
        localDataSource.mandiDao().insert(mandiEntity)

    suspend fun update(mandiEntity: MandiEntity) = localDataSource.mandiDao().update(mandiEntity)

    suspend fun delete(id: Int) = localDataSource.mandiDao().delete(id)
}