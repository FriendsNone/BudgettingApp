package io.catto.financely

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import io.catto.financely.data.TransactionRepository
import io.catto.financely.data.models.Transaction
import kotlinx.coroutines.launch

class MainViewModel(private val transactionRepository: TransactionRepository) : ViewModel() {
    fun getAllTransactions() = transactionRepository.getAllTransactions().asLiveData(viewModelScope.coroutineContext)

    fun getTransaction(id: Int) = transactionRepository.getTransaction(id).asLiveData(viewModelScope.coroutineContext)

    fun insertTransaction(transaction: Transaction) = viewModelScope.launch {
        val newTransaction = Transaction(
            0,
            transaction.date,
            transaction.category,
            transaction.description,
            transaction.amount,
            transaction.paymentMethod,
            transaction.type
        )
        transactionRepository.insertTransaction(newTransaction)
    }
}