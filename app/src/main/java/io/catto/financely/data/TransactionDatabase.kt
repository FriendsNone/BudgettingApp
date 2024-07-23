package io.catto.financely.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.catto.financely.data.converters.DateConverters
import io.catto.financely.data.daos.TransactionDao
import io.catto.financely.data.models.Transaction

@Database(entities = [Transaction::class], version = 1, exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        private fun buildDatabase(context: Context): TransactionDatabase {
            return Room.databaseBuilder(context, TransactionDatabase::class.java, "transaction.db")
                .build()
        }

        @Volatile
        private var INSTANCE: TransactionDatabase? = null

        fun getDatabaseInstance(context: Context): TransactionDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }
    }
}