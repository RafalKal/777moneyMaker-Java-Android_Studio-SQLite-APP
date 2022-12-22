package com.example.a777moneymaker.dataBaseColumnControllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;
import com.example.a777moneymaker.models.AccountModel;
import com.example.a777moneymaker.DataBaseHelper;
import java.util.ArrayList;
import java.util.List;

public class AccountsController extends DataBaseHelper {

    public AccountsController(@Nullable Context context) {
        super(context);
    }

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

}
