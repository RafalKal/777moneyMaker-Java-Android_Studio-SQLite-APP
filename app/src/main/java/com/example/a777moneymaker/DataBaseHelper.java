package com.example.a777moneymaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String USER_ACC_TABLE = "USER_ACC_TABLE";
    public static final String COLUMN_ACCOUNT_NAME = "ACCOUNT_NAME";
    public static final String COLUMN_MAIN_ACCOUNT = "MAIN_ACCOUNT";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "budgetTracker.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_ACC_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ACCOUNT_NAME + " TEXT, " + COLUMN_MAIN_ACCOUNT + " BOOL)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(AccountModel accountModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ACCOUNT_NAME, accountModel.getName());
        cv.put(COLUMN_MAIN_ACCOUNT, accountModel.isMainAcc());

        long insert = db.insert(USER_ACC_TABLE, null, cv);

        if (insert == -1){
            return false;
        }
        else {
            return true;
        }
    }
}
