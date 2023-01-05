package com.example.a777moneymaker;

import android.database.sqlite.SQLiteDatabase;

public class ApplicationState {

    private static String actualAccount = "Rafal";

    public static String getActualAccount() {
        return actualAccount;
    }

    public static void setActualAccount(String actualAccount) {
        ApplicationState.actualAccount = actualAccount;
    }
}
