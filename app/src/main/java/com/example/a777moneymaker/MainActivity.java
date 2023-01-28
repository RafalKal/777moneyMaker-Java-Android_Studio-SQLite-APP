package com.example.a777moneymaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.example.a777moneymaker.activities.AddAccountActivity;
import com.example.a777moneymaker.activities.AddCategoryActivity;
import com.example.a777moneymaker.activities.AddExpenseActivity;
import com.example.a777moneymaker.activities.AddIncomeActivity;
import com.example.a777moneymaker.models.NotificationModel;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_wallet_activity);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this);
        ApplicationState.setActualAccountModel(dbHelper.getAccountModelByID(1));
        if(!dbHelper.ifNotificationExist()) {
            dbHelper.addNotificationModel(new NotificationModel(1, 1000));
        }

    }

    public void showTransactions(View view){
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        Toast.makeText(this, dbHelper.getEveryTransaction().toString(), Toast.LENGTH_SHORT).show();
    }

    public void addExpense(View view){
        Intent intent = new Intent(getApplicationContext(), AddExpenseActivity.class);
        startActivity(intent);
    }

    public void addIncome(View view){
        Intent intent = new Intent(getApplicationContext(), AddIncomeActivity.class);
        startActivity(intent);
    }

    public void goToAddAccount(View view){
        Intent intent = new Intent(getApplicationContext(), AddAccountActivity.class);
        startActivity(intent);
    }

    public void goToAddCategory(View view){
        Intent intent = new Intent(getApplicationContext(), AddCategoryActivity.class);
        startActivity(intent);
    }

}