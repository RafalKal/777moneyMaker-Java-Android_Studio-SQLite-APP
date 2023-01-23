package com.example.a777moneymaker.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.a777moneymaker.ApplicationState;
import com.example.a777moneymaker.DataBaseHelper;
import com.example.a777moneymaker.R;
import com.example.a777moneymaker.models.ExpenseModel;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class WalletFragment extends Fragment {

    DataBaseHelper dbHelper;
    ListView transactionListView;
    SimpleCursorAdapter simpleCursorAdapter;
    ArrayAdapter<ExpenseModel> adapter;
    TextView accountNameText;
    TextView balanceText;
    String accountName = null;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public WalletFragment() {
        // EMPTY CONSTRUCTOR
    }

    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
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
    }

    @Override
    public void onResume() {

        if(ApplicationState.getActualAccountModel() != null){
            accountNameText.setText("Portfel " + ApplicationState.getActualAccountModel().getName());
            balanceText.setText(ApplicationState.getActualAccountModel().getBalance() + " zl");
            accountName = ApplicationState.getActualAccountModel().getName();
        }

        simpleCursorAdapter = dbHelper.transactionListViewFromDB(accountName);
        transactionListView.setAdapter(simpleCursorAdapter);

//        adapter = new ArrayAdapter<ExpenseModel>(this.getActivity(), R.layout.row_in_transaction_list, dbHelper.getEveryExpense());
//        transactionListView.setAdapter(adapter);

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // VIEW INITIATION, USES XML
        View myView = inflater.inflate(R.layout.fragment_wallet, container, false);

        accountNameText = myView.findViewById(R.id.accountNameText);
        balanceText = myView.findViewById(R.id.balanceText);
        accountName = null;
        if(ApplicationState.getActualAccountModel() != null){
            accountNameText.setText("Portfel " + ApplicationState.getActualAccountModel().getName());
            balanceText.setText(ApplicationState.getActualAccountModel().getBalance() + " zl");
            accountName = ApplicationState.getActualAccountModel().getName();
        }

        // DB HELPER FOR ADD ACCOUNT TO DATABASE
        dbHelper = new DataBaseHelper(this.getActivity());

        // ASSIGNING VIEWS FROM RESOURCES TO OBJECTS
        transactionListView = myView.findViewById(R.id.transactionListView);

        if(dbHelper.accountListViewFromDB()==null){
            Toast.makeText(WalletFragment.this.getActivity(), "function is null", Toast.LENGTH_LONG).show();
        }

//        ArrayList<ExpenseModel> expenseModelsList = (ArrayList<ExpenseModel>) dbHelper.getEveryExpense();
//
//
//        adapter = new ArrayAdapter<ExpenseModel>(this.getActivity(), R.layout.row_in_transaction_list, expenseModelsList);

        simpleCursorAdapter = dbHelper.transactionListViewFromDB(accountName);

//        transactionListView.setAdapter(adapter);

        // PILLS THE LIST WITH RECORDS FROM THE DATABASE
        transactionListView.setAdapter(simpleCursorAdapter);

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

}