package com.example.a777moneymaker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    // ACCOUNTS FINAL VARIABLES
    public static final String USER_ACC_TABLE = "USER_ACC_TABLE";
    public static final String COLUMN_ACCOUNT_ID = "ID_ACCOUNT";
    public static final String COLUMN_ACCOUNT_NAME = "ACCOUNT_NAME";
    public static final String COLUMN_MAIN_ACCOUNT = "MAIN_ACCOUNT";

    // EXPENSE FINAL VARIABLES
    public static final String EXPENSE_TABLE = "EXPENSE_TABLE";
    public static final String COLUMN_EXPENSE_ID = "ID_EXPENSE";
    public static final String COLUMN_EXPENSE_NAME = "EXPENSE_NAME";
    public static final String COLUMN_EXPENSE_DESCRIPTION = "EXPENSE_DESCRIPTION";
    public static final String COLUMN_EXPENSE_PRICE = "EXPENSE_PRICE";
    public static final String COLUMN_EXPENSE_CATEGORY = "EXPENSE_CATEGORY";
    public static final String COLUMN_EXPENSE_DATE = "EXPENSE_DATE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "budgetTracker.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // STATEMENT FOR CREATING ACCOUNT TABLE IN DATABASE
        String createAccountTableStatement = "CREATE TABLE " + USER_ACC_TABLE + " (" + COLUMN_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ACCOUNT_NAME + " TEXT, " + COLUMN_MAIN_ACCOUNT + " BOOL)";

        // STATEMENT FOR CREATING EXPENSE TABLE IN DATABASE
        String createExpenseTableStatement = "CREATE TABLE " + EXPENSE_TABLE + " (" + COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_EXPENSE_NAME + " TEXT, " + COLUMN_EXPENSE_DESCRIPTION + " TEXT, " + COLUMN_EXPENSE_PRICE + " REAL, " + COLUMN_EXPENSE_CATEGORY + " TEXT, " + COLUMN_EXPENSE_DATE + " TEXT)";

        // EXECUTION OF THE ABOVE
        db.execSQL(createAccountTableStatement);
        db.execSQL(createExpenseTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
