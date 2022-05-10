package com.rkg.mandi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.rkg.mandi.data.model.MandiEntity

@Dao
interface MandiDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMandi(mandi: MandiEntity)
}