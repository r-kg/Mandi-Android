package com.rkg.mandi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rkg.mandi.data.local.dao.MandiDao
import com.rkg.mandi.data.model.MandiEntity

@Database(version = 2, entities = [MandiEntity::class])
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_LABEL = "mandi_db"
    }

    abstract fun mandiDao(): MandiDao
}