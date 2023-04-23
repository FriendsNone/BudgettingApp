package com.middlename.budgetingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.middlename.budgetingapp.databinding.ActivityAddTransactionBinding;

import java.util.Date;

public class AddTransaction extends AppCompatActivity {
    ActivityAddTransactionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        boolean update = getIntent().getBooleanExtra("update", false);
        int id = getIntent().getIntExtra("id", -1);
        String name = getIntent().getStringExtra("name");
        long amount = getIntent().getLongExtra("amount", -1);
        String description = getIntent().getStringExtra("description");
        Date date = (Date) getIntent().getSerializableExtra("date");
        boolean isIncome = getIntent().getBooleanExtra("type", false);

        if (update) {
            binding.title.setText("Update");
            binding.setTransactionText.setText("Update");

            binding.delete.setVisibility(View.VISIBLE);

            binding.name.setText(name);
            binding.amount.setText(amount + "");
            binding.description.setText(description);
            binding.typeIncome.setChecked(isIncome);
        }

        binding.setTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String source = binding.name.getText().toString();
                String amount = binding.amount.getText().toString();
                String description = binding.description.getText().toString();
                boolean isIncome = binding.typeIncome.isChecked();

                TransactionsTable transactionsTable = new TransactionsTable();
                transactionsTable.setName(source);
                transactionsTable.setAmount(Long.parseLong(amount));
                transactionsTable.setDescription(description);
                transactionsTable.setDate(date == null ? new Date() : date);
                transactionsTable.setIncome(isIncome);

                TransactionsDatabase transactionsDatabase = TransactionsDatabase.getInstance(view.getContext());
                TransactionsDao transactionsDao = transactionsDatabase.getDao();

                if (update) {
                    transactionsTable.setId(id);
                    transactionsDao.update(transactionsTable);
                    finish();
                    return;
                }

                transactionsDao.insert(transactionsTable);
                finish();
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Delete Transaction")
                        .setMessage("Are you sure you want to delete this transaction?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TransactionsDatabase transactionsDatabase = TransactionsDatabase.getInstance(view.getContext());
                                TransactionsDao transactionsDao = transactionsDatabase.getDao();
                                transactionsDao.delete(id);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { dialog.cancel(); }
                        })
                        .show();
            }
        });
    }
}