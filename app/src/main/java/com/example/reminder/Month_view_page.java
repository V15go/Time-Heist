package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.reminder.CalenderUtils.daysInMonthArray;
import static com.example.reminder.CalenderUtils.monthYearFromDate;

public class Month_view_page extends AppCompatActivity implements calendarAdapter.OnitemListener {
    private TextView monthYeartext;
    private RecyclerView month_grid;
    private ImageView back_btn, front_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_view_page);

        month_grid = findViewById(R.id.month_grid);
        monthYeartext = findViewById(R.id.monthOf_year);
        back_btn = findViewById(R.id.back_month);
        front_btn = findViewById(R.id.front_month);
        CalenderUtils.selectedDate = LocalDate.now();
        setMonthView();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderUtils.selectedDate = CalenderUtils.selectedDate.minusMonths(1);
                setMonthView();
            }
        });


        front_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderUtils.selectedDate = CalenderUtils.selectedDate.plusMonths(1);
                setMonthView();
            }
        });

        





    }


    private void setMonthView()
    {
        monthYeartext.setText(monthYearFromDate(CalenderUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalenderUtils.selectedDate);

        calendarAdapter calendarAdapter = new calendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        month_grid.setLayoutManager(layoutManager);
        month_grid.setAdapter(calendarAdapter);
    }



    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalenderUtils.selectedDate = date;
            setMonthView();
        }
    }
}