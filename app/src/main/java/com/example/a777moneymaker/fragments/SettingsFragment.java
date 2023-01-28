package com.example.a777moneymaker.fragments;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.a777moneymaker.ApplicationState;
import com.example.a777moneymaker.DataBaseHelper;
import com.example.a777moneymaker.R;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Button setNotificationStateButton;
    RadioGroup notificationStateRadioGroup;
    RadioButton radioButtonNotificationState1;
    RadioButton radioButtonNotificationState2;

    DataBaseHelper dbHelper;

    public SettingsFragment() {}

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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


        View myView = inflater.inflate(R.layout.fragment_settings, container, false);

        if(ApplicationState.getActualAccountModel() != null){
            Toast.makeText(SettingsFragment.this.getActivity(), "Aktualne konto: " + ApplicationState.getActualAccountModel().getName(), Toast.LENGTH_LONG).show();
        }

        dbHelper = new DataBaseHelper(SettingsFragment.this.getActivity());

        setNotificationStateButton = myView.findViewById(R.id.setNotificationStateButton);
        notificationStateRadioGroup = myView.findViewById(R.id.notificationStateRadioGroup);
        radioButtonNotificationState1 = myView.findViewById(R.id.radioButtonNotificationState1);
        radioButtonNotificationState2 = myView.findViewById(R.id.radioButtonNotificationState2);

        if(dbHelper.getNotificationState() == 1){
            radioButtonNotificationState1.setChecked(true);
        }else if(dbHelper.getNotificationState() == 0){
            radioButtonNotificationState2.setChecked(true);
        }

        setNotificationStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id1 = radioButtonNotificationState1.getId();
                int id2 = radioButtonNotificationState2.getId();

                if(notificationStateRadioGroup.getCheckedRadioButtonId() == id1){
                    dbHelper.editNotificationState(1, dbHelper.getNotificationLimit());
                }else if(notificationStateRadioGroup.getCheckedRadioButtonId() == id2){
                    dbHelper.editNotificationState(0,  dbHelper.getNotificationLimit());
                }
            }
        });

        return myView;
    }
}