package com.example.a777moneymaker;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.a777moneymaker.dataBaseColumnControllers.ExpenseController;
import com.example.a777moneymaker.models.ExpenseModel;
import java.text.ParseException;

public class AddExpenseActivity extends AppCompatActivity {

    EditText nameTextView;
    EditText priceTextView;
    EditText dateTextView;
    Spinner categorySpinner;
    TextView shoppingListTextView;
    ExpenseModel expenseModel;
    ExpenseController dbExpenseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense_activity);

        Spinner dropdown = findViewById(R.id.categorySpinner);
        String[] categories = {"Jedzenie", "Napoje", "Paliwo", "Komunikacja", "Alkohol", "Papierosy", "Rozrywka", "Edukacja"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    public void addToShoppingList(View view) throws ParseException {
        nameTextView = findViewById(R.id.expenseName);
        priceTextView = findViewById(R.id.expensePrice);
        dateTextView = (EditText)findViewById(R.id.expenseDate);
        categorySpinner = findViewById(R.id.categorySpinner);
        shoppingListTextView = findViewById(R.id.shoppingList);

        String name = nameTextView.getText().toString();
        float price = Float.parseFloat(priceTextView.getText().toString());
        String category = categorySpinner.getSelectedItem().toString();


        nameTextView.setText(null);
        priceTextView.setText(null);
    }


    public void submitAddExpense(View view){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(AddExpenseActivity.this);
        expenseModel = new ExpenseModel(1, "AAA", "BBB", 12.12F, "AAA", "AAA");

        dbExpenseController = new ExpenseController(AddExpenseActivity.this);


        try {
            dbExpenseController.addExpenseModel(expenseModel);
            Toast.makeText(AddExpenseActivity.this, dbExpenseController.getEveryExpense().toString(), Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Toast.makeText(AddExpenseActivity.this, "Nie udalo sie dodac wydatku do db", Toast.LENGTH_LONG).show();
        }
        //finish();
    }

}