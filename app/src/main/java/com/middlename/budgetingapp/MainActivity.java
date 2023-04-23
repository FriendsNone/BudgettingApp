package com.middlename.budgetingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.middlename.budgetingapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickEvent {
    ActivityMainBinding binding;
    TransactionsAdapter transactionsAdapter;
    TransactionsDatabase transactionsDatabase;
    TransactionsDao transactionsDao;
    long balance = 0, income = 0, expense = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddTransaction.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        balance = 0;
        income = 0;
        expense = 0;

        transactionsDatabase = TransactionsDatabase.getInstance(this);
        transactionsDao = transactionsDatabase.getDao();
        transactionsAdapter = new TransactionsAdapter(this, this);
        binding.transactions.setAdapter(transactionsAdapter);
        binding.transactions.setAdapter(transactionsAdapter);
        binding.transactions.setLayoutManager(new LinearLayoutManager(this));

        List<TransactionsTable> transactionsTables = transactionsDao.getAll();

        for (TransactionsTable transactionsTable : transactionsTables) {
            if (transactionsTable.isIncome()) { income += transactionsTable.getAmount(); }
            else { expense += transactionsTable.getAmount(); }

            transactionsAdapter.add(transactionsTable);
        }
        balance = income - expense;

        binding.incomeAmount.setText(income + "");
        binding.expensesAmount.setText(expense + "");
        binding.balanceAmount.setText(balance + "");
    }

    @Override
    public void OnClick(int position) {
        Intent intent = new Intent(MainActivity.this, AddTransaction.class);
        intent.putExtra("update", true);
        intent.putExtra("id", transactionsAdapter.getTransactionId(position));
        intent.putExtra("name", transactionsAdapter.getTransactionName(position));
        intent.putExtra("amount", transactionsAdapter.getTransactionAmount(position));
        intent.putExtra("description", transactionsAdapter.getTransactionDescription(position));
        intent.putExtra("date", transactionsAdapter.getTransactionDate(position));
        intent.putExtra("type", transactionsAdapter.getTransactionType(position));
        startActivity(intent);
    }
}