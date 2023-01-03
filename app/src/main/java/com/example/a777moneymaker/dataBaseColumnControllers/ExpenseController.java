package com.example.a777moneymaker.dataBaseColumnControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;
import com.example.a777moneymaker.DataBaseHelper;
import com.example.a777moneymaker.models.ExpenseModel;
import java.util.ArrayList;
import java.util.List;

public class ExpenseController extends DataBaseHelper {

    public ExpenseController(@Nullable Context context) {
        super(context);
    }

    public boolean addExpenseModel(ExpenseModel expenseModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EXPENSE_NAME, expenseModel.getName());
        cv.put(COLUMN_EXPENSE_DESCRIPTION, expenseModel.getDescription());
        cv.put(COLUMN_EXPENSE_PRICE, expenseModel.getPrice());
        cv.put(COLUMN_EXPENSE_CATEGORY, expenseModel.getCategory());
        cv.put(COLUMN_EXPENSE_DATE, expenseModel.getDate());

        try {
            long insert = db.insert(EXPENSE_TABLE, null, cv);
            if (insert == -1){
                return false;
            }
            else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Nie udalo sie dodać do bazy danych - kontroller");
            return false;
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
