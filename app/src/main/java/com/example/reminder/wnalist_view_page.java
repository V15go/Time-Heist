package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class wnalist_view_page extends AppCompatActivity {

    private TextView title_want;
    private Button update_wantlist;
    private ListView listView_view_wantlist;

    ArrayList<String> grocery_list_view ;


     wantlist_adapter arrayAdapter_grocery_view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wnalist_view_page);

        title_want = findViewById(R.id.title_view_wantist);
        update_wantlist = findViewById(R.id.update_grocery_list);

        listView_view_wantlist = findViewById(R.id.listview_view_grcoery);


        Intent data  = getIntent();

        Bundle bundle  =getIntent().getExtras();

        grocery_list_view = data.getStringArrayListExtra("List");

        title_want.setText(data.getStringExtra("Title"));



        arrayAdapter_grocery_view = new wantlist_adapter(getApplicationContext(),grocery_list_view);

        listView_view_wantlist.setAdapter(arrayAdapter_grocery_view);

        update_wantlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wnalist_view_page.this, edit_notes_page.class);
                startActivity(intent);
            }
        });









    }
}