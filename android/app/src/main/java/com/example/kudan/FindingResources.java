package com.example.kudan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kudan.ARActivity.MainActivity;
import com.example.kudan.model.Book;
import com.example.kudan.model.BookAdapter;
import com.example.kudan.model.CategoryAdapter;

public class FindingResources extends AppCompatActivity {

    private int category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_resources);
        EditText editText = this.findViewById(R.id.finder);
        editText.setHint("Buscar por " + CategoryAdapter.Categories.get(Integer.parseInt(getIntent().getStringExtra("id"))));

        //Starting a grid view
        ListView listView = this.findViewById(R.id.results);
        // Instance of CategoryAdapter Class
        listView.setAdapter(new BookAdapter( this));
        /**
         * On Click event for Single ListView Item
         * */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Sending image id to FullScreenActivity
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                // passing array index
                Book book = (Book)parent.getItemAtPosition(position);
                i.putExtra("Book", book);
                startActivity(i);
            }
        });
    }
}
