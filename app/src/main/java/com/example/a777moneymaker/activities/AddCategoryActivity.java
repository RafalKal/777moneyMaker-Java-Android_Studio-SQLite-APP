package com.example.a777moneymaker.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.a777moneymaker.ApplicationState;
import com.example.a777moneymaker.DataBaseHelper;
import com.example.a777moneymaker.R;
import com.example.a777moneymaker.models.CategoryModel;

public class AddCategoryActivity extends AppCompatActivity {

    DataBaseHelper dbHelper;
    EditText categoryEditText;
    Button addCategoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // OPEN DATABASE HELPER / CONTROLLER
        dbHelper = new DataBaseHelper(AddCategoryActivity.this);

        // INIT LAYOUT FROM XML
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category_activity);

        if(ApplicationState.getActualAccountModel() != null){
            Toast.makeText(AddCategoryActivity.this, "Aktualne konto: " + ApplicationState.getActualAccountModel().getName(), Toast.LENGTH_LONG).show();
        }
        addCategoryButton = findViewById(R.id.addCategoryButton);

    }

    public void addCategory(View view){

        categoryEditText = findViewById(R.id.categoryEditText);

        String name = categoryEditText.getText().toString();

        CategoryModel categoryModel = new CategoryModel(name);

        dbHelper.addCategoryModel(categoryModel);
        ListView categoryListView = findViewById(R.id.categoriesListView);
        SimpleCursorAdapter simpleCursorAdapter = dbHelper.categoryListViewFromDB();

        finish();
    }
}
