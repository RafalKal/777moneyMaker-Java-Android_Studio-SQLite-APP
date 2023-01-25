package com.example.a777moneymaker.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

public class MyCategoriesAdapter extends SimpleCursorAdapter {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        return view;
    }

    public MyCategoriesAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
    }
}
