package com.example.a777moneymaker.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.a777moneymaker.ApplicationState;
import com.example.a777moneymaker.DataBaseHelper;
import com.example.a777moneymaker.R;

public class AccountFragment extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    DataBaseHelper dbHelper;
    ListView accountListView;
    SimpleCursorAdapter simpleCursorAdapter;

    public AccountFragment() {
        // EMPTY CONSTRUCTOR
    }

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
    public void onResume() {
        simpleCursorAdapter = dbHelper.accountListViewFromDB();
        accountListView.setAdapter(simpleCursorAdapter);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // VIEW INITIATION, USES XML
        View myView = inflater.inflate(R.layout.fragment_account, container, false);

        // DB HELPER FOR ADD ACCOUNT TO DATABASE
        dbHelper = new DataBaseHelper(this.getActivity());

        // ASSIGNING VIEWS FROM RESOURCES TO OBJECTS
        accountListView = myView.findViewById(R.id.accountsListView);

        if(dbHelper.accountListViewFromDB()==null){
            Toast.makeText(AccountFragment.this.getActivity(), "function is null", Toast.LENGTH_LONG).show();
        }

        simpleCursorAdapter = dbHelper.accountListViewFromDB();

        // PILLS THE LIST WITH RECORDS FROM THE DATABASE
        accountListView.setAdapter(simpleCursorAdapter);

        // WHEN CLICKED- COLORS THE SELECTED ROWS AND TELLS YOU (BY TOAST) WHICH ROW HAS BEEN SELECTED
        accountListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = (Cursor) simpleCursorAdapter.getItem(position);

                int accountID = cursor.getInt(0);
                String name = cursor.getString(1);

                ApplicationState.setActualAccountModel(dbHelper.getAccountModelByID(accountID));

                // COLOURS GREEN
                int color = Color.TRANSPARENT;
                Drawable background = view.getBackground();
                if (background instanceof ColorDrawable)
                    color = ((ColorDrawable) background).getColor();
                if(color != getResources().getColor(R.color.colorGreen))
                    view.setBackgroundColor(getResources().getColor(R.color.colorGreen));

                // COLOURS WHITE OTHERS
                for (int i = 0; i < accountListView.getCount(); i++) {
                    if (i != position) {
                        getViewByPosistion(i, accountListView).setBackgroundColor(Color.WHITE);
                    }
                }
                Toast.makeText(AccountFragment.this.getActivity(), "Ustawiono jako aktualne konto: " + ApplicationState.getActualAccountModel().getName(), Toast.LENGTH_SHORT).show();
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

}

