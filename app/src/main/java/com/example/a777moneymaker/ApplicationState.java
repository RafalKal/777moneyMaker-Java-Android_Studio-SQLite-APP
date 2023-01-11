package com.example.a777moneymaker;

import android.database.sqlite.SQLiteDatabase;

import com.example.a777moneymaker.models.AccountModel;

public class ApplicationState {

    private static AccountModel actualAccountModel;

    public static AccountModel getActualAccountModel() {
        return actualAccountModel;
    }

    public static void setActualAccountModel(AccountModel actualAccountModel) {
        ApplicationState.actualAccountModel = actualAccountModel;
    }

    public static String toString_() {
        return actualAccountModel.getName();
    }
}
