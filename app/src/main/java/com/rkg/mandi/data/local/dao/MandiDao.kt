package com.rkg.mandi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rkg.mandi.data.model.MandiEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MandiDao {

    @Query("SELECT * FROM MandiEntity")
    fun selectAll() : Flow<List<MandiEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(mandi: MandiEntity)
}