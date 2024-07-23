package io.catto.financely.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: Date,
    val category: String,
    val description: String,
    val amount: Double,
    val paymentMethod: String,
    val type: String
)