package com.example.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class addnotes_page extends AppCompatActivity {


    Button save_notes;

    EditText notes_title, notes_content;




    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnotes_page);
        
        save_notes = findViewById(R.id.save_notes_btn);
        notes_title = findViewById(R.id.title_notes);
        notes_content = findViewById(R.id.content_add);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        
        save_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = notes_title.getText().toString();
                String content = notes_content.getText().toString();
                
                if(title.isEmpty()){
                    Toast.makeText(addnotes_page.this,"Title is Empty",Toast.LENGTH_SHORT).show();;
                    notes_title.setError("Title is Empty");
                }
                else if(content.isEmpty()){
                    Toast.makeText(addnotes_page.this,"Content is Empty",Toast.LENGTH_SHORT).show();;
                    notes_content.setError("Content is Empty");

                }
                
                else{
                    Save_notes(title, content);
                }
            }

            private void Save_notes(String title, String content) {

                DocumentReference documentReference = firebaseFirestore.collection("Your Notes").document(firebaseUser.getUid()).collection("myNotes").document();
                Map<String,Object> my_notes = new HashMap<>();

                my_notes.put("Title",title);
                my_notes.put("Content",content);


                documentReference.set(my_notes).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Notes added to your pocket",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(addnotes_page.this, notes_page.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed to add notes. Once more try again",Toast.LENGTH_LONG).show();

                    }
                });

            }
        });




    }
}