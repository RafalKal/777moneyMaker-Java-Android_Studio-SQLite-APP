package com.example.a777moneymaker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.a777moneymaker.ApplicationState;
import com.example.a777moneymaker.DataBaseHelper;
import com.example.a777moneymaker.R;

public class NotificationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    DataBaseHelper dbHelper;

    Button setLimitButton;
    EditText limitEditText;
    TextView limitTextView;

    View myView;

    public NotificationFragment() {}

    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_notification, container, false);

        if(ApplicationState.getActualAccountModel() != null){
            Toast.makeText(NotificationFragment.this.getActivity(), "Aktualne konto: " + ApplicationState.getActualAccountModel().getName(), Toast.LENGTH_LONG).show();
        }

        dbHelper = new DataBaseHelper(NotificationFragment.this.getActivity());

        setLimitButton = myView.findViewById(R.id.setLimitButton);
        limitEditText = myView.findViewById(R.id.limitEditText);
        limitTextView = myView.findViewById(R.id.limitTextView);

        String textViewContent = "KWOTA DLA POWIADOMIEŃ: " + String.valueOf(dbHelper.getNotificationLimit());
        limitTextView.setText(textViewContent);

        setLimitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float limit = Float.parseFloat(limitEditText.getText().toString());
                dbHelper.editNotificationState(dbHelper.getNotificationState(), limit);
                String textViewContent = "KWOTA DLA POWIADOMIEŃ: " + String.valueOf(dbHelper.getNotificationLimit());
                limitTextView.setText(textViewContent);
            }
        });


        return myView;
    }
}