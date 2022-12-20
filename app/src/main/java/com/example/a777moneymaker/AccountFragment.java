package com.example.a777moneymaker;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    EditText accountEditText;
    Switch mainAccountSwitch;
    Button addAccountButton;
    TextView nameTextView;
    TextView booleanTextView;
    AccountModel accountModel;
    DataBaseHelper dataBaseHelper;


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
        addAccountButton = (Button) myView.findViewById(R.id.addAccountButton);
        addAccountButton.setOnClickListener(this);
        return myView;
        //return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onClick(View v) {
        accountEditText = getView().findViewById(R.id.accountEditText);
        mainAccountSwitch = getView().findViewById(R.id.mainAccountSwitch);
        nameTextView = getView().findViewById(R.id.nameTextView);
        booleanTextView = getView().findViewById(R.id.booleanTextView);

        String accountName = accountEditText.getText().toString();
        Boolean isMainAccount = (Boolean) mainAccountSwitch.isChecked();

        dataBaseHelper = new DataBaseHelper(AccountFragment.this.getActivity());
        try {
            nameTextView.setText(accountName);
            booleanTextView.setText(isMainAccount.toString());
            accountModel = new AccountModel(1, accountName, isMainAccount);
            Toast.makeText(AccountFragment.this.getActivity(), accountModel.getName(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(AccountFragment.this.getActivity(), "Nie udalo sie stworzyc obiektu accountModel", Toast.LENGTH_LONG).show();
        }

        try {
            wait(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            dataBaseHelper.addOne(accountModel);
            Toast.makeText(AccountFragment.this.getActivity(), accountModel.isMainAcc().toString(), Toast.LENGTH_SHORT);
        }catch (Exception e){
            Toast.makeText(AccountFragment.this.getActivity(), "Nie udalo sie dodac konta do bazy danych", Toast.LENGTH_LONG);
        }
    }
}

