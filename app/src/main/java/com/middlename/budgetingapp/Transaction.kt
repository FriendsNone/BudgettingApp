package com.middlename.budgetingapp

class Transaction(val name: String, val amount: Double) {
    override fun toString(): String {
        return "Transaction(name='$name', amount=$amount)"
    }
}