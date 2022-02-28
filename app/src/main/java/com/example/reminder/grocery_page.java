package com.example.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class grocery_page extends AppCompatActivity {

    FloatingActionButton add_wantlist_btn;
    private FirebaseAuth firebaseAuth;

    RecyclerView recycler_wantlist;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter<grocery_adapter, NoteViewHolder_wantList> wantlistAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_page);

        add_wantlist_btn = findViewById(R.id.add_wantlist_btn);
        recycler_wantlist = findViewById(R.id.recycler_wantlist);
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi_grocery);
        bottomNavigationView.setSelectedItemId(R.id.grocery_b);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.grocery_b:
                        return true;

                    case R.id.home_b:
                        startActivity(new Intent(grocery_page.this, MainActivity.class));
                        return true;
                    case R.id.notes_b:
                        startActivity(new Intent(grocery_page.this, notes_page.class));
                        return true;

                }
                return false;
            }
        });


        add_wantlist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), add_grocery.class);
                startActivity(intent);
            }
        });


        Query query = firebaseFirestore.collection("Your Wantlist").document(firebaseUser.getUid()).collection("wantlist").orderBy("Title", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<grocery_adapter> alluserwantlist = new FirestoreRecyclerOptions.Builder<grocery_adapter>().setQuery(query, grocery_adapter.class).build();

        wantlistAdapter = new FirestoreRecyclerAdapter<grocery_adapter, NoteViewHolder_wantList>(alluserwantlist) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder_wantList noteViewHolder, int i, @NonNull grocery_adapter grocery_adapter) {

                noteViewHolder.recycler_want_title.setText(grocery_adapter.getTitle());

                String listId = wantlistAdapter.getSnapshots().getSnapshot(i).getId();

                ImageView cancel_btn = noteViewHolder.itemView.findViewById(R.id.remove_item);

                ArrayList<String> er = grocery_adapter.getItem_list();



                noteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), wnalist_view_page.class);
                        intent.putExtra("Title",grocery_adapter.getTitle());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("List",er);
                        intent.putExtra("List",bundle);
                        intent.putExtra("listid",listId);
                        v.getContext().startActivity(intent);

                    }
                });


              /*  cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DocumentReference documentReference = firebaseFirestore.collection("Your Wantlist").document(firebaseUser.getUid()).collection("wantlist").document(listId);

                        new AlertDialog.Builder(grocery_page.this).setIcon(R.drawable.question)
                                .setTitle("Are You Sure?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                documentReference.delete();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(grocery_page.this, "Have A Good Day!",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                    }
                });
*/





            }

            @NonNull
            @Override
            public NoteViewHolder_wantList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_layout, parent, false);
                return new NoteViewHolder_wantList(view);
            }
        };


        recycler_wantlist.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycler_wantlist.setLayoutManager(staggeredGridLayoutManager);
        recycler_wantlist.setAdapter(wantlistAdapter);


    }


    class NoteViewHolder_wantList extends RecyclerView.ViewHolder {
        private TextView recycler_want_title;
        LinearLayout want_layout;


        public NoteViewHolder_wantList(@NonNull View itemView) {
            super(itemView);
            recycler_want_title = itemView.findViewById(R.id.recycler_want_title);
            want_layout = itemView.findViewById(R.id.want_layout);


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        wantlistAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (wantlistAdapter != null) {
            wantlistAdapter.startListening();
        }

    }


}





