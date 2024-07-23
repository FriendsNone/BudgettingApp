package com.middlename.budgetingapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var balanceTextView: TextView
    private lateinit var addTransactionFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        balanceTextView = findViewById(R.id.current_balance_text_view)
        addTransactionFab = findViewById(R.id.add_transaction_fab)

        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)

        addTransactionFab.setOnClickListener {
//            code
        }

        // Update balance text view with the current balance
        val currentBalance = getCurrentBalance()
        balanceTextView.text = getString(R.string.current_balance_template, currentBalance)

        showSampleData()
    }

    private fun getCurrentBalance(): Double {
        // TODO: Implement method to get the current balance
        return 0.0
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

//    show sample data to recycler view
    private fun showSampleData() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TransactionAdapter(getSampleData())
    }

    private fun getSampleData(): List<Transaction> {
        return listOf(
            Transaction("Sample Transaction 1", 100.0),
            Transaction("Sample Transaction 2", 200.0),
            Transaction("Sample Transaction 3", 300.0),
            Transaction("Sample Transaction 4", 400.0),
            Transaction("Sample Transaction 5", 500.0),
            Transaction("Sample Transaction 6", 600.0),
            Transaction("Sample Transaction 7", 700.0),
            Transaction("Sample Transaction 8", 800.0),
            Transaction("Sample Transaction 9", 900.0),
            Transaction("Sample Transaction 10", 1000.0),
            Transaction("Sample Transaction 11", 1100.0),
            Transaction("Sample Transaction 12", 1200.0),
            Transaction("Sample Transaction 13", 1300.0),
            Transaction("Sample Transaction 14", 1400.0),
            Transaction("Sample Transaction 15", 1500.0),
            Transaction("Sample Transaction 16", 1600.0),
            Transaction("Sample Transaction 17", 1700.0),
            Transaction("Sample Transaction 18", 1800.0),
            Transaction("Sample Transaction 19", 1900.0),
            Transaction("Sample Transaction 20", 2000.0),
            Transaction("Sample Transaction 21", 2100.0),
            Transaction("Sample Transaction 22", 2200.0),
            Transaction("Sample Transaction 23", 2300.0),
            Transaction("Sample Transaction 24", 2400.0),
            Transaction("Sample Transaction 25", 2500.0),
            Transaction("Sample Transaction 26", 2600.0),
            Transaction("Sample Transaction 27", 2700.0),
            Transaction("Sample Transaction 28", 2800.0),
            Transaction("Sample Transaction 29", 2900.0),
            Transaction("Sample Transaction 30", 3000.0)
        )
    }
}

