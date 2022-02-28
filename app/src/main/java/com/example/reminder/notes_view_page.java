package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class notes_view_page extends AppCompatActivity {
    private TextView title_view, content_view;
    private Button edit_view_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_view_page);


        title_view = findViewById(R.id.view_title);
        content_view = findViewById(R.id.view_content);
        edit_view_button = findViewById(R.id.edit_view_btn);


        Intent data = getIntent();
        edit_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(notes_view_page.this, edit_notes_page.class);
                intent.putExtra("Title",data.getStringExtra("Title"));
                intent.putExtra("Content",data.getStringExtra("Content"));
                intent.putExtra("notesid",data.getStringExtra("notesid"));
                startActivity(intent);

            }
        });

        content_view.setText(data.getStringExtra("Content"));
        title_view.setText(data.getStringExtra("Title"));
    }
}