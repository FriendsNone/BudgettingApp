package com.middlename.budgetingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(private val transactions: List<Transaction>) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.bind(transaction)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val transactionNameTextView: TextView = itemView.findViewById(R.id.transaction_name_text_view)
        private val transactionAmountTextView: TextView = itemView.findViewById(R.id.transaction_amount_text_view)

        fun bind(transaction: Transaction) {
            transactionNameTextView.text = transaction.name
            transactionAmountTextView.text = transaction.amount.toString()
        }
    }
}