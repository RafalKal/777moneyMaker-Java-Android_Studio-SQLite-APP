package com.example.a777moneymaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Sidebar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wallet);
    }

    public void addExpense(View view){
//        Intent intent = new Intent(getApplicationContext(), AddExpenseActivity.class);
        Intent intent = new Intent(getApplicationContext(), SimpleRecyclerViewActivity.class);
        startActivity(intent);
    }

}