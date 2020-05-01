package com.example.kudan.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kudan.R;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {

    protected Activity activity;
    public static ArrayList<String> Categories;

    public CategoryAdapter(Activity activity) {
        this.activity = activity;
        this.Categories = new ArrayList<>();
        Categories.add("Libros");
        Categories.add("Presentaciones");
        Categories.add("Manuales");
        Categories.add("Guias");
        Categories.add("Notas");
        Categories.add("Documentos");

    }

    @Override
    public int getCount() {
        return this.Categories.size();
    }

    @Override
    public Object getItem(int position) {
        return this.Categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.activity_category, null);
        }

        TextView textView = v.findViewById(R.id.textView);
        textView.setText(this.Categories.get(position));

        return v;
    }
}
