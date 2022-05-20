package com.rkg.mandi.data.local.dao

import androidx.room.*
import com.rkg.mandi.data.model.MandiEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MandiDao {

    @Query("SELECT * FROM MandiEntity")
    fun selectAll(): Flow<List<MandiEntity>>

    @Query("SELECT * FROM MandiEntity WHERE id == :id")
    suspend fun selectById(id: Int): List<MandiEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(mandi: MandiEntity)

    @Update
    suspend fun update(mandi: MandiEntity): Int

    @Query("DELETE FROM MandiEntity WHERE id = :id")
    suspend fun delete(id: Int)
}