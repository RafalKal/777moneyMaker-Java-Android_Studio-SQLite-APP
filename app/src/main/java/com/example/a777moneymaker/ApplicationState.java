package com.example.a777moneymaker;

import com.example.a777moneymaker.models.AccountModel;

public class ApplicationState {
    private static AccountModel actualAccountModel;

    public static AccountModel getActualAccountModel() {
        return actualAccountModel;
    }

    public static void setActualAccountModel(AccountModel actualAccountModel) {
        ApplicationState.actualAccountModel = actualAccountModel;
    }
}
