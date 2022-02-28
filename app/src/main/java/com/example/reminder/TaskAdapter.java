package com.example.reminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter(@NonNull Context context, List<Task> events)
    {
        super(context, 0, events);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Task event = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_layout, parent, false);

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

        String eventTitle = event.getTitle() +" "+ CalenderUtils.formattedTime(event.getTime());
        eventCellTV.setText(eventTitle);
        return convertView;
    }
}
