package com.example.a777moneymaker.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.example.a777moneymaker.R;

public class MyTransactionAdapter extends SimpleCursorAdapter {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        TextView tv = (TextView) view.findViewById(R.id.transactionType);

        String transactionType = tv.getText().toString();

        if(transactionType.equals("WYDATEK")){
            view.setBackgroundColor(Color.rgb(247, 152, 148));
        }
        else {
            view.setBackgroundColor(Color.rgb(207, 247, 158));
        }
        return view;
    }

    public MyTransactionAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
    }
}
