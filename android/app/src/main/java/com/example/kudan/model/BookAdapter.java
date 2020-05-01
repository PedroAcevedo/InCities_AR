package com.example.kudan.model;

import android.app.Activity;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kudan.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BooleanSupplier;

public class BookAdapter extends BaseAdapter {

    protected ArrayList<Book> books;
    protected Activity activity;

    public BookAdapter(Activity activity) {
        this.activity = activity;
        HashMap<String,String> aug = new HashMap<>();
        aug.put("Kudan Lego Marker.jpg","Kudan Cow.png" );
        aug.put("CarmenRicardo.png","Carmen_Ricardo_Video.mp4" );
        aug.put("CarmenAssets2.png","Interculturalidad.png" );
        Book book = new Book("Prueba1","Testeo de la APP", aug, "ALL");
        this.books = new ArrayList<>();
        books.add(book);
    }

    @Override
    public int getCount() {
        return this.books.size();
    }

    @Override
    public Object getItem(int position) {
        return this.books.get(position);
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
            v = inf.inflate(R.layout.activity_book_item, null);
        }

        Book book = this.books.get(position);
        TextView description = v.findViewById(R.id.description);
        TextView name = v.findViewById(R.id.title);
        name.setText(book.getName());
        description.setText(book.getDescription());

        return v;
    }
}
