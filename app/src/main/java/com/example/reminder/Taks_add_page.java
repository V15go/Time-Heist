package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Taks_add_page extends AppCompatActivity {

    private EditText time_event, date_event, title_event;
    private SwitchCompat switch_repeat;

    private Button save_event;
    int thour,tmin, ehour,emin;

    private LocalTime time;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taks_add_page);
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        title_event = findViewById(R.id.title_task);
        date_event = findViewById(R.id.date_task);
        time_event = findViewById(R.id.time_task);
        save_event = findViewById(R.id.addt_task_btn);
        switch_repeat = findViewById(R.id.switch_repeat);

        date_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Taks_add_page.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,onDateSetListener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String date;
                if(month<=9)
                    date = dayOfMonth+"/0"+month +"/"+ year;
                else
                   date = dayOfMonth+"/"+ month +"/"+ year;
                date_event.setText(date);

            }
        };


        time_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Taks_add_page.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                thour = hourOfDay;
                                tmin = minute;
                                String time = thour + ":"+tmin;
                                SimpleDateFormat hours24format = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = hours24format.parse(time);
                                    SimpleDateFormat hour24format = new SimpleDateFormat("HH:mm");
                                    time_event.setText(hour24format.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        },24,0,false


                );

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(thour,tmin);
                timePickerDialog.show();
            }
        });





        save_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title = title_event.getText().toString();
                String Date = date_event.getText().toString();
                String Time = time_event.getText().toString();
                boolean sw = switch_repeat.isChecked();
                if(Title.isEmpty()){
                    title_event.setError("Title is Empty");
                }
                else{
                    save_events_Action(Title, Date, Time,sw);
                }
            }

            private void save_events_Action(String title, String date, String time, boolean sw) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                DateTimeFormatter dt1 = DateTimeFormatter.ofPattern("HH:mm");

                LocalDate localDate = LocalDate.parse(date,dtf);
                LocalTime localTime = LocalTime.parse(time,dt1);

                Task task = new Task(title,localDate,localTime,sw);

                Task.eventsList.add(task);

                startActivity(new Intent(Taks_add_page.this, MainActivity.class));
                finish();



            }
        });




    }
}