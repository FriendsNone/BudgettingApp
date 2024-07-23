package io.catto.financely.data

import io.catto.financely.data.models.Transaction

class TransactionRepository(private val transactionDatabase: TransactionDatabase) {
    suspend fun insertTransaction(transaction: Transaction) {
        transactionDatabase.transactionDao().insertTransaction(transaction)
    }

    fun getAllTransactions() = transactionDatabase.transactionDao().getAllTransactions()

    fun getTransaction(id: Int) = transactionDatabase.transactionDao().getTransaction(id)
}