package com.example.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class notes_page extends AppCompatActivity {

    FloatingActionButton notes_add_btn;
    private FirebaseAuth firebaseAuth;

    RecyclerView recyclerView_notes;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter<firebasemodel, NoteViewHolder> notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_page);
        notes_add_btn = findViewById(R.id.add_notes_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView_notes = findViewById(R.id.notes_recycler);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi_notes);

        bottomNavigationView.setSelectedItemId(R.id.notes_b);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.notes_b:
                        return true;

                    case R.id.home_b:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        return true;
                    case R.id.grocery_b:
                        startActivity(new Intent(getApplicationContext(),grocery_page.class));
                        return true;

                }
                return false;
            }
        });

        notes_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(notes_page.this, addnotes_page.class));
                finish();
            }
        });

        Query query = firebaseFirestore.collection("Your Notes").document(firebaseUser.getUid()).collection("myNotes").orderBy("Title", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<firebasemodel> allusernotes = new FirestoreRecyclerOptions.Builder<firebasemodel>().setQuery(query, firebasemodel.class).build();

        notesAdapter = new FirestoreRecyclerAdapter<firebasemodel, NoteViewHolder>(allusernotes) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i, @NonNull firebasemodel firebasemodel) {
                ImageView popupmenu = noteViewHolder.itemView.findViewById(R.id.pop_notes);



               int colorcode = getRandomColor();
                noteViewHolder.notes_l.setBackgroundColor(noteViewHolder.itemView.getResources().getColor(colorcode,null));


                noteViewHolder.recycler_title.setText(firebasemodel.getTitle());
                noteViewHolder.recycler_content.setText(firebasemodel.getContent());

                String notesId = notesAdapter.getSnapshots().getSnapshot(i).getId();
                noteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),notes_view_page.class);
                        intent.putExtra("Title",firebasemodel.getTitle());
                        intent.putExtra("Content",firebasemodel.getContent());
                        intent.putExtra("notesid",notesId);

                        v.getContext().startActivity(intent);

                    }
                });



                popupmenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                        popupMenu.setGravity(Gravity.END);
                        popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {

                                Intent intent = new Intent(v.getContext(),edit_notes_page.class);
                                intent.putExtra("Title",firebasemodel.getTitle());
                                intent.putExtra("Content",firebasemodel.getContent());
                                intent.putExtra("notesid",notesId);
                                v.getContext().startActivity(intent);
                                return false;
                            }
                        });
                        popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {

                                DocumentReference documentReference = firebaseFirestore.collection("Your Notes").document(firebaseUser.getUid()).collection("myNotes").document(notesId);
                                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(v.getContext(),"The note is deleted",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(v.getContext(),"Try Again",Toast.LENGTH_SHORT).show();

                                    }
                                });



                                return false;
                            }
                        });
                    }
                });






            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout, parent, false);
                return new NoteViewHolder(view);
            }
        };

        recyclerView_notes.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView_notes.setLayoutManager(staggeredGridLayoutManager);
        recyclerView_notes.setAdapter(notesAdapter);


    }

    public  class NoteViewHolder extends RecyclerView.ViewHolder {
        private  TextView recycler_title;
        private  TextView recycler_content;
        LinearLayout notes_l;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            recycler_title = itemView.findViewById(R.id.recycler_title);
            recycler_content = itemView.findViewById(R.id.recycler_content);

            notes_l = itemView.findViewById(R.id.note);


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        notesAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (notesAdapter != null) {
            notesAdapter.startListening();
        }

    }

    private int getRandomColor(){
        List<Integer> colorcode = new ArrayList<>();
        colorcode.add(R.color.retro_orange);
        colorcode.add(R.color.retro_purple);
        colorcode.add(R.color.retro_green);
        colorcode.add(R.color.retro_green2);
        colorcode.add(R.color.retro_red);
        colorcode.add(R.color.retro_yellow);

        Random random = new Random();
        int num = random.nextInt(colorcode.size());
        return colorcode.get(num);


    }


}
