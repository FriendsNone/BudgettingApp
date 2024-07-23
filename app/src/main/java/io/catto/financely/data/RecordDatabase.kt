package io.catto.financely.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.catto.financely.data.converters.DateConverters
import io.catto.financely.data.daos.RecordDao
import io.catto.financely.data.models.Record

@Database(entities = [Record::class], version = 1, exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class RecordDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
}