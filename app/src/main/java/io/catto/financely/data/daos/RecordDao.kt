package io.catto.financely.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.catto.financely.data.models.Record
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecord(record: Record): Long

    @Delete
    suspend fun deleteRecord(record: Record): Int

    @Update
    suspend fun updateRecord(record: Record): Int

    @Query("SELECT * FROM record ORDER BY date DESC")
    fun getAllRecords(): Flow<List<Record>>

    @Query("SELECT * FROM record WHERE id = :id")
    fun getRecord(id: Int): Flow<Record>

    @Query("SELECT * FROM record WHERE type = :type ORDER BY date DESC")
    fun getRecordsOfType(type: String): Flow<List<Record>>

    @Query("SELECT * FROM record WHERE date BETWEEN :start AND :end ORDER BY date DESC")
    fun getRecordsBetweenDate(start: Long, end: Long): Flow<List<Record>>
    
    @Query("SELECT SUM(amount) FROM record WHERE type = :type")
    fun getSumOfRecordsOfType(type: String): Flow<Double>
}