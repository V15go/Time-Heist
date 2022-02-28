package com.example.reminder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class calendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final ArrayList<LocalDate> days;
    public final View highlight;
    public final TextView dayOfMonth;
    private final calendarAdapter.OnitemListener onitemListener;
    public calendarViewHolder(ArrayList<LocalDate> days, @NonNull View itemView, calendarAdapter.OnitemListener onitemListener) {
        super(itemView);
        highlight = itemView.findViewById(R.id.highlight);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.onitemListener = onitemListener;
        itemView.setOnClickListener(this);
        this.days = days;

    }

    @Override
    public void onClick(View v) {
        onitemListener.onItemClick(getAdapterPosition(),days.get(getAdapterPosition()));
    }
}
