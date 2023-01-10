package com.example.a777moneymaker.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.a777moneymaker.ApplicationState;
import com.example.a777moneymaker.DataBaseHelper;
import com.example.a777moneymaker.R;
import com.example.a777moneymaker.models.ExpenseModel;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {

    EditText nameTextView;
    EditText priceTextView;
    Button addToShoppingListButton;
    Spinner categorySpinner;
    DatePickerDialog datePickerDialog;
    Button dateButton;
    ExpenseModel expenseModel;
    DataBaseHelper dbHelper;
    ListView itemsListView;
    ArrayAdapter<String> adapter;
    ArrayList<String> itemsList;
    int dayA;
    int monthA;
    int yearA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // OPEN DATABASE HELPER / CONTROLLER
        dbHelper = new DataBaseHelper(AddExpenseActivity.this);

        // INIT LAYOUT FROM XML
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense_activity);

        // '+' BUTTON
        addToShoppingListButton = findViewById(R.id.addToShoppingListButton);

        // CATEGORY SPINNER
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

        // DATEPICKER THINGS...
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());



        // ARRAY LIST CONTAINING Expenses.toStrings
        itemsList = new ArrayList<>();

        // LIST VIEW
        itemsListView = (ListView) findViewById(R.id.itemsListView);
    }

    //--------------------------------------------------------------------------------------\
    // FUNCTIONS FOR DATA PICKER                                                            |
    //--------------------------------------------------------------------------------------/
    private String getMonthFormat(int month){
        if(month==1)
            return "JAN";
        if(month==2)
            return "FEB";
        if(month==3)
            return "MAR";
        if(month==4)
            return "APR";
        if(month==5)
            return "MAY";
        if(month==6)
            return "JUN";
        if(month==7)
            return "JUL";
        if(month==8)
            return "AUG";
        if(month==9)
            return "SEP";
        if(month==10)
            return "OCT";
        if(month==11)
            return "NOV";
        if(month==12)
            return "DEC";
        return "JAN";
    }

    private String getTodaysDate(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return makeDateString(day, month, year);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;

                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                dayA = day;
                monthA = month;
                yearA = year;
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
    //--------------------------------------------------------------------------------------|


    public void submitAddExpense(View view) {

        dbHelper = new DataBaseHelper(AddExpenseActivity.this);

        expenseModel = new ExpenseModel("Snickers", "Snickers Piotr i Pawel", 2.99F, "Jedzenie", ApplicationState.getActualAccount(), dayA, monthA, yearA);

        try {
            dbHelper.addExpenseModel(expenseModel);
            Toast.makeText(AddExpenseActivity.this, dbHelper.getEveryExpense().toString(), Toast.LENGTH_LONG).show();

        }catch (Exception e) {
            Toast.makeText(AddExpenseActivity.this, "Nie udalo sie dodac wydatku do db", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        //finish();
    }

    // '+' BUTTON
    public void addToShoppingList(View view) throws ParseException {

        // LINKING TO COMPONENTS FROM XML LAYOUT USING ID
        nameTextView = findViewById(R.id.expenseName);
        priceTextView = findViewById(R.id.expensePrice);
        categorySpinner = findViewById(R.id.categorySpinner);
        dateButton.getText();

        // GET TEXTS FROM INPUTS
        String name = nameTextView.getText().toString();
        String description = "*opis*";
        float price = Float.parseFloat(priceTextView.getText().toString());
        String category = categorySpinner.getSelectedItem().toString();
        String date = (String) dateButton.getText();

        // NEW EXPENSE OBJECT
        expenseModel = new ExpenseModel("Snickers", "Snickers Piotr i Pawel", 2.99F, "Jedzenie", ApplicationState.getActualAccount(), dayA, monthA, yearA);

        // ADDING NEW STRING TO ARRAYLIST
        itemsList.add(expenseModel.toString());

        // ADAPTER FOR FILLING THE DATA LISTVIEW
        adapter = new ArrayAdapter<String>(this, R.layout.row_in_expense_list, itemsList);

        // FILLING THE LISTVIEW WITH DATA
        itemsListView.setAdapter(adapter);

        // SET INPUTS TO DEFAULT INITIAL VALUES
        nameTextView.setText(null);
        priceTextView.setText(null);
    }
}