package com.middlename.budgetingapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TransactionsTable.class}, version = 1)
public abstract class TransactionsDatabase extends RoomDatabase {
    public abstract TransactionsDao getDao();
    public static volatile TransactionsDatabase INSTANCE;

    public static TransactionsDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, TransactionsDatabase.class, "transactions")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
