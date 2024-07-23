package io.catto.financely.data

import io.catto.financely.data.daos.RecordDao
import io.catto.financely.data.models.Record
import javax.inject.Inject

class RecordRepository @Inject constructor(private val recordDao: RecordDao) {
    suspend fun insertRecord(record: Record) { recordDao.insertRecord(record) }

    suspend fun deleteRecord(record: Record) { recordDao.deleteRecord(record) }

    suspend fun updateRecord(record: Record) { recordDao.updateRecord(record) }

    fun getAllRecords() = recordDao.getAllRecords()

    fun getRecord(id: Int) = recordDao.getRecord(id)

    fun getRecordsOfType(type: String) = recordDao.getRecordsOfType(type)

    fun getRecordsBetweenDate(start: Long, end: Long) = recordDao.getRecordsBetweenDate(start, end)
    
    fun getSumOfRecordsOfType(type: String) = recordDao.getSumOfRecordsOfType(type)
}