package com.example.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class add_grocery extends AppCompatActivity {

    EditText title_wantlist, input_list;

    ImageView add_list_item;

    Button save_grocery_btn;

   static ArrayList<String> grocery_list ;

   static wantlist_adapter arrayAdapter_grocery;



    ListView listView_grocery;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);


        title_wantlist = findViewById(R.id.title_wantist);
        input_list = findViewById(R.id.input_list);
        add_list_item = findViewById(R.id.add_list_btn);
        save_grocery_btn = findViewById(R.id.save_grocery_list);
        listView_grocery = findViewById(R.id.listview_grcoery);


        grocery_list = new ArrayList<>();


        arrayAdapter_grocery = new wantlist_adapter(getApplicationContext(),grocery_list);

        listView_grocery.setAdapter(arrayAdapter_grocery);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        add_list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = input_list.getText().toString();
                if(item.isEmpty()){
                    Toast.makeText(add_grocery.this,"No items Entered",Toast.LENGTH_SHORT).show();
                    input_list.setError("No items Entered");

                }
                else{
                    grocery_list.add(item);
                    listView_grocery.setAdapter(arrayAdapter_grocery);
                    input_list.setText("");
                    Toast.makeText(add_grocery.this,"Added",Toast.LENGTH_SHORT).show();

                }
            }
        });


        listView_grocery.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                remove_item(position);
                return false;
            }
        });


        save_grocery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = title_wantlist.getText().toString();
                if(title.isEmpty()){
                    Toast.makeText(add_grocery.this,"Enter the title",Toast.LENGTH_SHORT).show();
                    title_wantlist.setError("Enter the title");
                }
                else if(grocery_list.isEmpty()){
                    Toast.makeText(add_grocery.this,"No item entered",Toast.LENGTH_SHORT).show();
                    input_list.setError("No item entered");
                }
                else{
                    save_list(title,grocery_list);
                }
            }

            private void save_list(String title, ArrayList<String> grocery_list) {


                DocumentReference documentReference = firebaseFirestore.collection("Your Wantlist").document(firebaseUser.getUid()).collection("wantlist").document();
                Map<String,Object> grocery = new HashMap<>();

                grocery.put("Title",title);
                grocery.put("List",grocery_list);

                documentReference.set(grocery).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Want-List added to your pocket",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(add_grocery.this, grocery_page.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed to add Want-List. Try again later",Toast.LENGTH_LONG).show();

                    }
                });

            }
        });













    }


    public static void remove_item(int i){
        grocery_list.remove(i);
        arrayAdapter_grocery.notifyDataSetChanged();
    }
}