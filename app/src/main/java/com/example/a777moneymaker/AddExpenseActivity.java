package com.example.a777moneymaker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {

    EditText nameTextView;
    EditText priceTextView;
    EditText dateTextView;
    Spinner categorySpinner;
    TextView shoppingListTextView;
    EditText datePicker;

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

        datePicker = findViewById(R.id.datePicker);
        Calendar calendar = java.util.Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(java.util.Calendar.YEAR, year);
                calendar.set(java.util.Calendar.MONTH, month);
                calendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
            }

            private void updateCalendar(){
                String Format = "MM/dd/yy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Format, Locale.US);

                datePicker.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        datePicker.setOnClickListener(v -> new DatePickerDialog(AddExpenseActivity.this, date,
                                    calendar.get(java.util.Calendar.YEAR),
                                    calendar.get(java.util.Calendar.MONTH),
                                    calendar.get(java.util.Calendar.DAY_OF_MONTH)).show());

//        Expense expense = new Expense(name, price, category);
//
//        shoppingListTextView.setText(shoppingListTextView.getText()
//                + "\nnazwa: " + expense.getName() + ", "
//                + expense.getPrice() + "PLN, data: "
//                + date + ", kat: " +  expense.getCategory() + "\n");

        nameTextView.setText(null);
        priceTextView.setText(null);
    }


    public void submitAddExpense(View view){
        finish();
    }

}