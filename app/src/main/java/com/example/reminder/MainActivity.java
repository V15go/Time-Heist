package com.example.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.reminder.CalenderUtils.daysInWeekArray;
import static com.example.reminder.CalenderUtils.monthYearFromDate;

public class MainActivity extends AppCompatActivity implements calendarAdapter.OnitemListener {


    private TextView month_of_week;
    private RecyclerView weekly_view;
    private ImageView back_week, front_week;
    private ListView events_list;
    private FloatingActionButton add_events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        weekly_view = findViewById(R.id.week_grid);
        back_week = findViewById(R.id.back_mweek);
        front_week = findViewById(R.id.front_week);
        events_list = findViewById(R.id.listView_events);
        month_of_week = findViewById(R.id.month_Of_week);
        add_events = findViewById(R.id.add_event_btn);
        CalenderUtils.selectedDate = LocalDate.now();
        setWeekView();






        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi_home);

        bottomNavigationView.setSelectedItemId(R.id.home_b);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home_b:
                        startActivity(new Intent(getApplicationContext(),Month_view_page.class));
                        return true;

                    case R.id.notes_b:
                        startActivity(new Intent(getApplicationContext(),notes_page.class));
                        return true;
                    case R.id.grocery_b:
                        startActivity(new Intent(getApplicationContext(),grocery_page.class));
                        return true;

                }
                return false;
            }




        });



        back_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderUtils.selectedDate = CalenderUtils.selectedDate.minusWeeks(1);
                setWeekView();
            }
        });
        front_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderUtils.selectedDate = CalenderUtils.selectedDate.plusWeeks(1);
                setWeekView();
            }
        });


        add_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Taks_add_page.class));
            }
        });







    }

    private void setWeekView() {
        month_of_week.setText(monthYearFromDate(CalenderUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalenderUtils.selectedDate);

       calendarAdapter calendarAdapter = new calendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        weekly_view.setLayoutManager(layoutManager);
        weekly_view.setAdapter(calendarAdapter);
        setEventAdpater();



    }



    private void setEventAdpater() {
        ArrayList<Task> dailyEvents = Task.eventsForDate(CalenderUtils.selectedDate);
        TaskAdapter eventAdapter = new TaskAdapter(getApplicationContext(), dailyEvents);
        events_list.setAdapter(eventAdapter);


    }


    @Override
    public void onItemClick(int position, LocalDate date) {

        CalenderUtils.selectedDate = date;
        setWeekView();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();
    }
}