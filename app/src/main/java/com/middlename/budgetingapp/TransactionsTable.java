package com.middlename.budgetingapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "transactions")
public class TransactionsTable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "amount")
    private long amount;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "date")
    @TypeConverters(DateConverter.class)
    private Date date;

    @ColumnInfo(name = "isIncome")
    private boolean isIncome;

    public TransactionsTable() { }

    public TransactionsTable(int id, String source, long amount, String description, Date date, boolean isIncome) {
        this.id = id;
        this.name = source;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.isIncome = isIncome;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public boolean isIncome() { return isIncome; }
    public void setIncome(boolean income) { isIncome = income; }
}
