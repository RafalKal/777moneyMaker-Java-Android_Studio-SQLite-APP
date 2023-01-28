package com.example.a777moneymaker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.a777moneymaker.ApplicationState;
import com.example.a777moneymaker.MyNotificationManager;
import com.example.a777moneymaker.R;

public class NotificationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Button notificationButton;

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

        notificationButton = myView.findViewById(R.id.notificationButton);

        Context context = NotificationFragment.this.getActivity();

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MyNotificationManager.createNotificationChannel(context, "channel", "WalletChannel", "WalletChannelDescription");
                        MyNotificationManager.addNotification(context, "channel", "Malo kasy", "Brakuje kasy - Brakuje kasy - Brakuje kasy - Brakuje kasy - ");
                    }
                }, 6000);
            }
        });

        return myView;
    }
}