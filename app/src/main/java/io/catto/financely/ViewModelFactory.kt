package io.catto.financely

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.catto.financely.data.TransactionRepository

class ViewModelFactory(private val transactionRepository: TransactionRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(transactionRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}