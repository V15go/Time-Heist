package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class edit_notes_page extends AppCompatActivity {
    EditText edit_title, edit_content;
    Button update_btn;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes_page);
        update_btn = findViewById(R.id.save_edit_btn);
        edit_title = findViewById(R.id.save_edit_title);
        edit_content = findViewById(R.id.save_edit_content);


        Intent data = getIntent();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edit_title.getText().toString();
                String content = edit_content.getText().toString();

                if(title.isEmpty()){
                    Toast.makeText(edit_notes_page.this,"Title is Empty",Toast.LENGTH_SHORT).show();;
                    edit_title.setError("Title is Empty");
                }
                else if(content.isEmpty()){
                    Toast.makeText(edit_notes_page.this,"Content is Empty",Toast.LENGTH_SHORT).show();;
                    edit_content.setError("Content is Empty");

                }
                else{
                    DocumentReference documentReference = firebaseFirestore.collection("Your Notes").document(firebaseUser.getUid()).collection("myNotes").document(data.getStringExtra("notesid"));
                    Map<String, Object> note = new HashMap<>();
                    note.put("Title",title);
                    note.put("Content",content);
                    documentReference.set(note);

                    startActivity(new Intent(edit_notes_page.this, notes_page.class));
                 }
            }
        });

        String update_title = data.getStringExtra("Title");
        String update_content = data.getStringExtra("Content");
        edit_title.setText(update_title);
        edit_content.setText(update_content);


    }
}