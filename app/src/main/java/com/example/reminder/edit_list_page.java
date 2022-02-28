package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class edit_list_page extends AppCompatActivity {

    EditText title_edit, edit_list;

    Button change_list;

    ImageView edit_list_btn;
    ArrayList<String> grocery_list_edit ;

    ListView edit_view_grcoery;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    wantlist_adapter arrayAdapter_grocery_change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list_page);


        title_edit = findViewById(R.id.title_wantist_edit);

        edit_list = findViewById(R.id.edit_list);

        change_list = findViewById(R.id.change_grocery_list);

        edit_list_btn = findViewById(R.id.edit_list_btn);
        edit_view_grcoery  = findViewById(R.id.edit_view_grcoery);

        Intent data = getIntent();

        grocery_list_edit = data.getStringArrayListExtra("List");

        arrayAdapter_grocery_change = new wantlist_adapter(getApplicationContext(),grocery_list_edit);



        edit_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = edit_list.getText().toString();
                if(item.isEmpty()){
                    Toast.makeText(edit_list_page.this,"No items Entered",Toast.LENGTH_SHORT).show();
                    edit_list.setError("No items Entered");

                }
                else{
                    grocery_list_edit.add(item);
                    edit_view_grcoery.setAdapter(arrayAdapter_grocery_change);
                    edit_list.setText("");
                    Toast.makeText(edit_list_page.this,"Added",Toast.LENGTH_SHORT).show();

                }
            }
        });



        change_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = title_edit.getText().toString();
                if(title.isEmpty()){
                    Toast.makeText(edit_list_page.this,"Enter the title",Toast.LENGTH_SHORT).show();
                    title_edit.setError("Enter the title");
                }
                else if(grocery_list_edit.isEmpty()){
                    Toast.makeText(edit_list_page.this,"No item entered",Toast.LENGTH_SHORT).show();
                    edit_list.setError("No item entered");
                }
                else{
                    DocumentReference documentReference = firebaseFirestore.collection("Your Wantlist").document(firebaseUser.getUid()).collection("wantlist").document(data.getStringExtra("notesid"));
                    Map<String, Object> wantlist = new HashMap<>();
                    wantlist.put("Title",title);
                    wantlist.put("List",grocery_list_edit);
                    documentReference.update(wantlist);
                }
            }
        });

        edit_view_grcoery.setAdapter(arrayAdapter_grocery_change);

        title_edit.setText(data.getStringExtra("Title"));

    }
}