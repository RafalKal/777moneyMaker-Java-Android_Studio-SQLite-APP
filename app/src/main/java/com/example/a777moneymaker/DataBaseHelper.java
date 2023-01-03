package com.example.a777moneymaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import com.example.a777moneymaker.models.AccountModel;
import com.example.a777moneymaker.models.ExpenseModel;
import java.util.ArrayList;
import java.util.List;
public class DataBaseHelper extends SQLiteOpenHelper {

    // _ACCOUNTS_ FINAL VARIABLES
    public static final String USER_ACC_TABLE = "USER_ACC_TABLE";
    public static final String COLUMN_ACCOUNT_ID = "ID_ACCOUNT";
    public static final String COLUMN_ACCOUNT_NAME = "ACCOUNT_NAME";
    public static final String COLUMN_MAIN_ACCOUNT = "MAIN_ACCOUNT";

    // _EXPENSE_ FINAL VARIABLES
    public static final String EXPENSE_TABLE = "EXPENSE_TABLE";
    public static final String COLUMN_EXPENSE_ID = "ID_EXPENSE";
    public static final String COLUMN_EXPENSE_NAME = "EXPENSE_NAME";
    public static final String COLUMN_EXPENSE_DESCRIPTION = "EXPENSE_DESCRIPTION";
    public static final String COLUMN_EXPENSE_PRICE = "EXPENSE_PRICE";
    public static final String COLUMN_EXPENSE_CATEGORY = "EXPENSE_CATEGORY";
    public static final String COLUMN_EXPENSE_DATE = "EXPENSE_DATE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "budgetTracker2.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // STATEMENT FOR CREATING ACCOUNT TABLE IN DATABASE
        String createAccountTableStatement = "CREATE TABLE " + USER_ACC_TABLE + " (" + COLUMN_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ACCOUNT_NAME + " TEXT, " + COLUMN_MAIN_ACCOUNT + " BOOL)";

        // STATEMENT FOR CREATING EXPENSE TABLE IN DATABASE
        String createExpenseTableStatement = "CREATE TABLE " + EXPENSE_TABLE + " (" + COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXPENSE_NAME + " TEXT, " + COLUMN_EXPENSE_DESCRIPTION + " TEXT, " + COLUMN_EXPENSE_PRICE + " REAL, " + COLUMN_EXPENSE_CATEGORY + " TEXT, " + COLUMN_EXPENSE_DATE + " TEXT)";

        //  EXECUTION OF THE ABOVE
            db.execSQL(createExpenseTableStatement);
            db.execSQL(createAccountTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // ---------------------\
    // _ACCOUNT_ FUNCTIONS  |
    // ---------------------/
    public boolean addAccountModel(AccountModel accountModel){

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

    public boolean deleteAccountModel(AccountModel accountModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + USER_ACC_TABLE + " WHERE " + COLUMN_ACCOUNT_ID + " = " + accountModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            return true;
        }else {
            return false;
        }
    }

    public List<AccountModel> getEveryAccount() {
        List<AccountModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + USER_ACC_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int accountID = cursor.getInt(0);
                String name = cursor.getString(1);
                boolean isMainAcc = cursor.getInt(2) == 1 ? true: false;

                AccountModel newAccount = new AccountModel(accountID, name, isMainAcc);
                returnList.add(newAccount);
            }while (cursor.moveToNext());
        }else {
            // empty section
        }
        cursor.close();
        db.close();
        return returnList;
    }

    // ---------------------\
    // _EXPENSE_ FUNCTIONS  |
    // ---------------------/
    public boolean addExpenseModel(ExpenseModel expenseModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EXPENSE_NAME, expenseModel.getName());
        cv.put(COLUMN_EXPENSE_DESCRIPTION, expenseModel.getDescription());
        cv.put(COLUMN_EXPENSE_PRICE, expenseModel.getPrice());
        cv.put(COLUMN_EXPENSE_CATEGORY, expenseModel.getCategory());
        cv.put(COLUMN_EXPENSE_DATE, expenseModel.getDate());

        long insert = db.insert(EXPENSE_TABLE, null, cv);

        if (insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean deleteExpenseModel(ExpenseModel expenseModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + EXPENSE_TABLE + " WHERE " + COLUMN_EXPENSE_ID + " = " + expenseModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            return true;
        }else {
            return false;
        }
    }

    public List<ExpenseModel> getEveryExpense() {
        List<ExpenseModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + EXPENSE_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int expenseID = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                float price = cursor.getFloat(3);
                String category = cursor.getString(4);
                String date = cursor.getString(5);

                ExpenseModel newExpense = new ExpenseModel(expenseID, name, description, price, category, date);
                returnList.add(newExpense);
            }while (cursor.moveToNext());
        }else {
            // empty section
        }
        cursor.close();
        db.close();
        return returnList;
    }

}
