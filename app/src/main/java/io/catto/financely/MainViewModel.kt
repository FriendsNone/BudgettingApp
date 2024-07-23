package io.catto.financely

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.catto.financely.data.RecordRepository
import io.catto.financely.data.models.Record
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val recordRepository: RecordRepository) : ViewModel() {
    fun insertRecord(record: Record) = viewModelScope.launch { recordRepository.insertRecord(record) }

    fun deleteRecord(record: Record) = viewModelScope.launch { recordRepository.deleteRecord(record) }

    fun updateRecord(record: Record) = viewModelScope.launch { recordRepository.updateRecord(record) }

    fun getAllRecords() = recordRepository.getAllRecords().asLiveData(viewModelScope.coroutineContext)

    fun getRecord(id: Int) = recordRepository.getRecord(id).asLiveData(viewModelScope.coroutineContext)

    fun getRecordsOfType(type: String) = recordRepository.getRecordsOfType(type).asLiveData(viewModelScope.coroutineContext)

    fun getRecordsBetweenDate(start: Long, end: Long) = recordRepository.getRecordsBetweenDate(start, end).asLiveData(viewModelScope.coroutineContext)
    
    fun getSumOfRecordsOfType(type: String) = recordRepository.getSumOfRecordsOfType(type).asLiveData(viewModelScope.coroutineContext)
}