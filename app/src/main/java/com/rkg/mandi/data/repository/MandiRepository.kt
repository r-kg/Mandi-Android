package com.rkg.mandi.data.repository

import com.rkg.mandi.data.local.AppDatabase
import com.rkg.mandi.data.model.MandiEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MandiRepository @Inject constructor(
    private val localDataSource: AppDatabase
) {
    suspend fun insert(mandiEntity: MandiEntity) =
        localDataSource.mandiDao().insertMandi(mandiEntity)
}