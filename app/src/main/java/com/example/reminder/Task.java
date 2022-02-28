package com.example.reminder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Task {


    public static ArrayList<Task> eventsList = new ArrayList<>();

    public static ArrayList<Task> eventsForDate(LocalDate date)
    {
        ArrayList<Task> events = new ArrayList<>();



            for (Task event : eventsList) {
                if (event.getDate().equals(date))
                    events.add(event);
            }

            return events;


    }


    private String Title;
    private LocalDate Date;
    private LocalTime Time;
    private Boolean sw;

    public Task(String Title, LocalDate Date, LocalTime Time, Boolean sw){
        this.Title  = Title;
        this.Date = Date;
        this.Time = Time;
        this.sw  = sw;
    }

    public Boolean getSw() {
        return sw;
    }

    public void setSw(Boolean sw) {
        this.sw = sw;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public LocalTime getTime() {
        return Time;
    }

    public void setTime(LocalTime time) {
        Time = time;
    }
}
