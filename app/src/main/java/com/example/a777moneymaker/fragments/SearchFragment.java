package com.example.a777moneymaker.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.a777moneymaker.ApplicationState;
import com.example.a777moneymaker.DataBaseHelper;
import com.example.a777moneymaker.MyNotificationManager;
import com.example.a777moneymaker.R;
import com.example.a777moneymaker.adapters.MyTransactionAdapter;
import com.example.a777moneymaker.models.AccountModel;
import com.example.a777moneymaker.models.CategoryModel;
import com.example.a777moneymaker.models.TransactionModel;

public class SearchFragment extends Fragment {

    DataBaseHelper dbHelper;
    ListView transactionWithPhraseListView;
    MyTransactionAdapter simpleCursorAdapter;
    TextView findTextView;
    EditText phraseEditText;
    Button findPhraseButton;
    String accountName = null;

    EditText nameEditText;
    EditText descriptionEditText;
    EditText priceEditText;
    Spinner categorySpinner;
    EditText accountEditText;
    EditText typeEditText;
    EditText dayEditText;
    EditText monthEditText;
    EditText yearEditText;

    Button submitButton;
    Button deleteButton;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // EMPTY CONSTRUCTOR
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if(ApplicationState.getActualAccountModel() != null){
            accountName = ApplicationState.getActualAccountModel().getName();
        }else Toast.makeText(SearchFragment.this.getActivity(), "XDXDXDXDXDX", Toast.LENGTH_LONG);

        //simpleCursorAdapter = dbHelper.transactionListViewFromDB(accountName);
        //transactionWithPhraseListView.setAdapter(simpleCursorAdapter);
    }

    @Override
    public void onResume() {

        if(ApplicationState.getActualAccountModel() != null){
            accountName = ApplicationState.getActualAccountModel().getName();
        }else Toast.makeText(SearchFragment.this.getActivity(), "XDXDXDXDXDX", Toast.LENGTH_LONG);

        simpleCursorAdapter = dbHelper.specialListViewFromDB1(accountName, phraseEditText.getText().toString());
        transactionWithPhraseListView.setAdapter(simpleCursorAdapter);

        super.onResume();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // VIEW INITIATION, USES XML
        LayoutInflater lf = getActivity().getLayoutInflater();

        View myView = lf.inflate(R.layout.fragment_search, container, false);

        findTextView = myView.findViewById(R.id.findTextView);
        phraseEditText = myView.findViewById(R.id.phraseEditText);
        findPhraseButton = myView.findViewById(R.id.findPhraseButton);

        if(ApplicationState.getActualAccountModel() != null){
            accountName = ApplicationState.getActualAccountModel().getName();
        }else {
            accountName = "account does not exist";
        }


        // DB HELPER FOR ADD ACCOUNT TO DATABASE
        dbHelper = new DataBaseHelper(this.getActivity());
        // ASSIGNING VIEWS FROM RESOURCES TO OBJECTS
        transactionWithPhraseListView = myView.findViewById(R.id.transactionWithPhraseListView);

        if(dbHelper.accountListViewFromDB()==null){
            Toast.makeText(SearchFragment.this.getActivity(), "function is null", Toast.LENGTH_LONG).show();
        }
        simpleCursorAdapter = dbHelper.transactionListViewFromDB(accountName);

        // PILLS THE LIST WITH RECORDS FROM THE DATABASE
        transactionWithPhraseListView.setAdapter(simpleCursorAdapter);

        transactionWithPhraseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) simpleCursorAdapter.getItem(position);

                int trasactionID = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                float price = cursor.getFloat(3);
                String category = cursor.getString(4);
                String account = cursor.getString(5);
                String type = cursor.getString(6);
                int day = cursor.getInt(7);
                int month = cursor.getInt(8);
                int year = cursor.getInt(9);

                createTransactionEditDialog(trasactionID, name, description, price, category, account, type, day, month, year);
            }
        });

        findPhraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phrase = phraseEditText.getText().toString();
                simpleCursorAdapter = dbHelper.specialListViewFromDB2(accountName, phrase);
                transactionWithPhraseListView.setAdapter(simpleCursorAdapter);
            }
        });

        return myView;
    }

    // GET LISTVIEW ROW PASSING INDEX PARAMETER
    public View getViewByPosistion(int position, ListView listView){
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if(position < firstListItemPosition || position > lastListItemPosition){
            return listView.getAdapter().getView(position, null, listView);
        }else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    public void createTransactionEditDialog(int transactionID, String name, String description, float price, String category, String account, String type, int day, int month, int year){

        dialogBuilder = new AlertDialog.Builder(this.getActivity());
        final View transactionEditPopupView = getLayoutInflater().inflate(R.layout.popup1_transaction_edit, null);

        categorySpinner = transactionEditPopupView.findViewById(R.id.categorySpinner);

        CategoryModel[] categoriesObjects = dbHelper.getEveryCategory().toArray(new CategoryModel[0]);
        String[] categories = new String[categoriesObjects.length];
        for(int i = 0; i < categories.length; i++) {
            categories[i] = categoriesObjects[i].getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchFragment.this.getActivity(), android.R.layout.simple_spinner_item, categories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(SearchFragment.this.getActivity(), value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        nameEditText = transactionEditPopupView.findViewById(R.id.nameEditText);
        descriptionEditText = transactionEditPopupView.findViewById(R.id.descriptionEditText);
        priceEditText = transactionEditPopupView.findViewById(R.id.priceEditText);
        accountEditText = transactionEditPopupView.findViewById(R.id.accountEditText);
        typeEditText = transactionEditPopupView.findViewById(R.id.typeEditText);
        dayEditText = transactionEditPopupView.findViewById(R.id.dayEditText);
        monthEditText = transactionEditPopupView.findViewById(R.id.monthEditText);
        yearEditText = transactionEditPopupView.findViewById(R.id.yearEditText);

        nameEditText.setText(name);
        descriptionEditText.setText(description);
        priceEditText.setText(Float.toString(price));
        categorySpinner.setSelection(getIndex(categorySpinner, category));
        accountEditText.setText(account);
        typeEditText.setText(type);
        dayEditText.setText(Integer.toString(day));
        monthEditText.setText(Integer.toString(month));
        yearEditText.setText(Integer.toString(year));

        float priceBeforeChange = Float.parseFloat(priceEditText.getText().toString());

        submitButton = transactionEditPopupView.findViewById(R.id.submitButton);
        deleteButton = transactionEditPopupView.findViewById(R.id.deleteButton);

        dialogBuilder.setView(transactionEditPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float priceAfterChange = Float.parseFloat(priceEditText.getText().toString());
                float result = priceAfterChange - priceBeforeChange;
                dbHelper.editTransactionModel(transactionID,
                        nameEditText.getText().toString(),
                        descriptionEditText.getText().toString(),
                        Float.parseFloat(priceEditText.getText().toString()),
                        categorySpinner.getSelectedItem().toString(),
                        accountEditText.getText().toString(),
                        typeEditText.getText().toString(),
                        Integer.parseInt(dayEditText.getText().toString()),
                        Integer.parseInt(monthEditText.getText().toString()),
                        Integer.parseInt(yearEditText.getText().toString())
                );

                if(typeEditText.getText().toString().equals("WYDATEK") && result>0){
                    dbHelper.editAccountModel(dbHelper.getAccountModelByName(account).getId(), account, dbHelper.getAccountModelByName(account).getBalance() - Math.abs(result));
                }
                else if(typeEditText.getText().toString().equals("WYDATEK") && result<0){
                    dbHelper.editAccountModel(dbHelper.getAccountModelByName(account).getId(), account, dbHelper.getAccountModelByName(account).getBalance() + Math.abs(result));
                }
                else if(typeEditText.getText().toString().equals("WPŁYW") && result>0){
                    dbHelper.editAccountModel(dbHelper.getAccountModelByName(account).getId(), account, dbHelper.getAccountModelByName(account).getBalance() + Math.abs(result));
                }
                else if(typeEditText.getText().toString().equals("WPŁYW") && result<0){
                    dbHelper.editAccountModel(dbHelper.getAccountModelByName(account).getId(), account, dbHelper.getAccountModelByName(account).getBalance() - Math.abs(result));
                }

                if(dbHelper.getAccountModelByName(account).getBalance() < 1000){
                    Context context = SearchFragment.this.getActivity();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MyNotificationManager.createNotificationChannel(context, "channel", "WalletChannel", "WalletChannelDescription");
                            MyNotificationManager.addNotification(context, "channel", "Malo kasy", "Brakuje kasy - Brakuje kasy - Brakuje kasy - Brakuje kasy - ");
                        }
                    }, 6000);
                }

                simpleCursorAdapter = dbHelper.transactionListViewFromDB(accountName);
                transactionWithPhraseListView.setAdapter(simpleCursorAdapter);

                dialog.dismiss();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionModel transaction = dbHelper.getTransactionModelByID(transactionID);
                AccountModel account = dbHelper.getAccountModelByName(transaction.getAccount());

                if(transaction.getType().equals("WYDATEK")){
                    dbHelper.editAccountModel(account.getId(), account.getName(), account.getBalance() + transaction.getPrice());
                }else {
                    dbHelper.editAccountModel(account.getId(), account.getName(), account.getBalance() - transaction.getPrice());
                }

                if(dbHelper.getAccountModelByName(account.getName()).getBalance() < 1000){
                    Context context = SearchFragment.this.getActivity();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MyNotificationManager.createNotificationChannel(context, "channel", "WalletChannel", "WalletChannelDescription");
                            MyNotificationManager.addNotification(context, "channel", "Malo kasy", "Brakuje kasy - Brakuje kasy - Brakuje kasy - Brakuje kasy - ");
                        }
                    }, 6000);
                }

                dbHelper.deleteTransactionModel(transactionID);

                simpleCursorAdapter = dbHelper.transactionListViewFromDB(accountName);
                transactionWithPhraseListView.setAdapter(simpleCursorAdapter);

                dialog.dismiss();
            }
        });
    }

    private int getIndex(Spinner categorySpinner, String category) {

        for( int i=0; i<categorySpinner.getCount(); i++){
            if(categorySpinner.getItemAtPosition(i).toString().equalsIgnoreCase(category)){
                return i;
            }
        }
        return 0;
    }

}