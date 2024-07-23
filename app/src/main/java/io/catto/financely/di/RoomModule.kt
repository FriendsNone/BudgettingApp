package io.catto.financely.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.catto.financely.data.RecordDatabase
import io.catto.financely.data.daos.RecordDao

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    fun provideRecordDatabase(@ApplicationContext context: Context): RecordDatabase {
        return Room.databaseBuilder(context, RecordDatabase::class.java, "record-db")
            .build()
    }

    @Provides
    fun provideRecordDao(recordDatabase: RecordDatabase): RecordDao {
        return recordDatabase.recordDao()
    }
}