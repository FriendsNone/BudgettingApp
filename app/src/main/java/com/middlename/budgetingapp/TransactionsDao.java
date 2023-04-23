package com.middlename.budgetingapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransactionsDao {
    @Insert
    void insert(TransactionsTable transactionsTable);

    @Update
    void update(TransactionsTable transactionsTable);

    @Query("DELETE FROM transactions WHERE id = :id")
    void delete(int id);

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    List<TransactionsTable> getAll();
}
