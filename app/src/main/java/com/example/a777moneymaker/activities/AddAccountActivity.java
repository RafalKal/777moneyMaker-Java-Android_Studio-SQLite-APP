package com.example.a777moneymaker.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a777moneymaker.ApplicationState;
import com.example.a777moneymaker.DataBaseHelper;
import com.example.a777moneymaker.R;
import com.example.a777moneymaker.models.AccountModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class AddAccountActivity extends AppCompatActivity {

    DataBaseHelper dbHelper;
    Button addAccountButton;
    Button showAccountsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // OPEN DATABASE HELPER / CONTROLLER
        dbHelper = new DataBaseHelper(AddAccountActivity.this);

        // INIT LAYOUT FROM XML
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account_activity);

        if(ApplicationState.getActualAccount()=="Rafal"){
            Toast.makeText(AddAccountActivity.this, "aktualne konto: Rafal", Toast.LENGTH_LONG).show();
        }

        addAccountButton = findViewById(R.id.addAccountButton);
        showAccountsButton = findViewById(R.id.showAccountsButton);
        //addAccountButton.setOnClickListener(this);

        showAccountsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHelper = new DataBaseHelper(AddAccountActivity.this);

                List<AccountModel> everyAccount = dbHelper.getEveryAccount();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");

//                String currentDateAndTime = sdf.format(new Date());
//
//                Toast.makeText(AccountFragment.this.getActivity(), currentDateAndTime, Toast.LENGTH_LONG).show();

                Toast.makeText(AddAccountActivity.this, everyAccount.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

//    @Override
//    public void onClick(View v) {
//
//        accountEditText = getView().findViewById(R.id.accountEditText);
//        mainAccountSwitch = getView().findViewById(R.id.mainAccountSwitch);
//        nameTextView = getView().findViewById(R.id.nameTextView);
//        booleanTextView = getView().findViewById(R.id.booleanTextView);
//
//        String accountName = accountEditText.getText().toString();
//        Boolean isMainAccount = (Boolean) mainAccountSwitch.isChecked();
//
//        dbHelper = new DataBaseHelper(AccountFragment.this.getActivity());
//
//        try {
//            nameTextView.setText(accountName);
//            booleanTextView.setText(isMainAccount.toString());
//            accountModel = new AccountModel(accountName, isMainAccount);
//            Toast.makeText(AccountFragment.this.getActivity(), "Udalo sie stworzyc obiekt: accountModel", Toast.LENGTH_LONG).show();
//        }catch (Exception e){
//            Toast.makeText(AccountFragment.this.getActivity(), "Nie udalo sie stworzyc obiektu accountModel", Toast.LENGTH_LONG).show();
//        }
//
//        try {
//            dbHelper.addAccountModel(accountModel);
//            Toast.makeText(AccountFragment.this.getActivity(), "Udalo sie dodac konto do bazy danych", Toast.LENGTH_LONG);
//        }catch (Exception e){
//            Toast.makeText(AccountFragment.this.getActivity(), "Nie udalo sie dodac konta do bazy danych", Toast.LENGTH_LONG);
//        }
//    }
}
