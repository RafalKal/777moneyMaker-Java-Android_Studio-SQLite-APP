package com.example.a777moneymaker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.a777moneymaker.DataBaseHelper;
import com.example.a777moneymaker.R;

public class CategoryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    DataBaseHelper dbHelper;
    ListView categoryListView;
    SimpleCursorAdapter simpleCursorAdapter;

    public CategoryFragment() {
        // EMPTY CONSTRUCTOR
    }

    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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
        simpleCursorAdapter = dbHelper.categoryListViewFromDB();
        categoryListView.setAdapter(simpleCursorAdapter);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // VIEW INITIATION, USES XML
        View myView = inflater.inflate(R.layout.fragment_category, container, false);

        // DB HELPER FOR ADD ACCOUNT TO DATABASE
        dbHelper = new DataBaseHelper(this.getActivity());

        // ASSIGNING VIEWS FROM RESOURCES TO OBJECTS
        categoryListView = myView.findViewById(R.id.categoriesListView);

        if(dbHelper.categoryListViewFromDB()==null){
        Toast.makeText(CategoryFragment.this.getActivity(), "function is null", Toast.LENGTH_LONG).show();
    }

        simpleCursorAdapter = dbHelper.categoryListViewFromDB();

        // PILLS THE LIST WITH RECORDS FROM THE DATABASE
        categoryListView.setAdapter(simpleCursorAdapter);

        return myView;
    }
}