package com.example.a777moneymaker;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Random;

public class SimpleRecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_recycler_view);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        String[] possibleCategories = {"Jedzenie", "Picie", "Paliwo", "Komunikacja", "Alkohol", "Papierosy"};

        String[] categories = new String[100];
        Random random = new Random();
        for (int i = 0; i < categories.length; i++) {
            categories[i] = possibleCategories[random.nextInt(possibleCategories.length)] + " " + (i + 1);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CategoriesAdapter(categories));
    }

    private static class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {

        private String[] categories;

        public CategoriesAdapter(String[] categories) {
            this.categories = categories;
        }

        public static class CategoryViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public CategoryViewHolder(TextView v) {
                super(v);
                textView = v;
            }
        }

        @NonNull
        @Override
        public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

            CategoryViewHolder viewHolder = new CategoryViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
            String category = categories[position];
            holder.textView.setText(category);
        }

        @Override
        public int getItemCount() {
            return categories.length;
        }
    }

}