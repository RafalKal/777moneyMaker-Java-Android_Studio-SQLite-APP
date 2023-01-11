package com.example.a777moneymaker.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a777moneymaker.ApplicationState;
import com.example.a777moneymaker.DataBaseHelper;
import com.example.a777moneymaker.R;
import com.example.a777moneymaker.fragments.AccountFragment;
import com.example.a777moneymaker.models.AccountModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class AddAccountActivity extends AppCompatActivity {

    DataBaseHelper dbHelper;
    EditText accountEditText;
    EditText balanceEditText;
    Switch isMainAccSwitch;
    Button addAccountButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // OPEN DATABASE HELPER / CONTROLLER
        dbHelper = new DataBaseHelper(AddAccountActivity.this);

        // INIT LAYOUT FROM XML
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account_activity);

        Toast.makeText(AddAccountActivity.this, "Aktualne konto: " + ApplicationState.toString_(), Toast.LENGTH_LONG).show();

        addAccountButton = findViewById(R.id.addAccountButton);

    }

    public void addAccount(View view){

        accountEditText = findViewById(R.id.accountEditText);
        balanceEditText = findViewById(R.id.balanceEditText);
        isMainAccSwitch = findViewById(R.id.mainAccountSwitch);

        String name = accountEditText.getText().toString();
        float balance = Float.parseFloat(balanceEditText.getText().toString());
        boolean isMainAcc = isMainAccSwitch.isChecked();

        AccountModel accountModel;
        if(balanceEditText.getText() == null){
            accountModel = new AccountModel(name, isMainAcc);
        }else{
            accountModel = new AccountModel(name, isMainAcc, balance);
        }

        dbHelper.addAccountModel(accountModel);
        ListView accountListView = findViewById(R.id.accountsListView);
        SimpleCursorAdapter simpleCursorAdapter = dbHelper.accountListViewFromDB();

        finish();
    }
}
