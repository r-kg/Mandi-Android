package com.rkg.mandi

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rkg.mandi.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_LABEL)
            .fallbackToDestructiveMigration().build()
}