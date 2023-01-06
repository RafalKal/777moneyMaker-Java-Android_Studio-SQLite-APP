package com.example.a777moneymaker.fragments;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.example.a777moneymaker.ApplicationState;
import com.example.a777moneymaker.DataBaseHelper;
import com.example.a777moneymaker.R;
import com.example.a777moneymaker.models.AccountModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class AccountFragment extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    DataBaseHelper dbHelper;
    ListView accountListView;


    public AccountFragment() {}

    public static AccountFragment newInstance(String param1, String param2) {

        AccountFragment fragment = new AccountFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_account, container, false);

        AccountModel accountModel = new AccountModel("Rafal", true);

        dbHelper = new DataBaseHelper(this.getActivity());

        dbHelper.addAccountModel(accountModel);

        accountListView = myView.findViewById(R.id.accountsListView);
        SimpleCursorAdapter simpleCursorAdapter = dbHelper.accountLisViewFromDB();
        accountListView.setAdapter(simpleCursorAdapter);
        accountListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) simpleCursorAdapter.getItem(position);
                String name = cursor.getString(1);
                Toast.makeText(AccountFragment.this.getActivity(), name, Toast.LENGTH_LONG).show();
            }
        });
        return myView;
    }

}

