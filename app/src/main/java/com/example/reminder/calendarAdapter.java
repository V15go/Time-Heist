package com.example.reminder;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class calendarAdapter extends RecyclerView.Adapter<calendarViewHolder> {

    private final ArrayList<LocalDate> days;

    private final OnitemListener onitemListener;

    public calendarAdapter(ArrayList<LocalDate> days, OnitemListener onitemListener) {
        this.days = days;
        this.onitemListener = onitemListener;
    }

    @NonNull
    @Override
    public calendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell,parent,false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (days.size()>15)
            layoutParams.height = (int)(parent.getHeight()*0.166666666);
        else
            layoutParams.height = (int) parent.getHeight();



        return new calendarViewHolder(days, view, onitemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull calendarViewHolder holder, int position) {
        final  LocalDate date = days.get(position);
        if (date == null)
            holder.dayOfMonth.setText("");
        else {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(CalenderUtils.selectedDate))
                holder.highlight.setBackgroundColor(Color.YELLOW);


        }

        }

    @Override
    public int getItemCount() {


        return days.size();
    }

    public interface OnitemListener{
        void onItemClick(int position, LocalDate date);
    }
}
