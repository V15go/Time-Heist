package com.example.reminder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class wantlist_adapter extends ArrayAdapter<String> {
String title_wantlist;
ArrayList<String> grocery_list;
Context context;
    public wantlist_adapter(Context context,ArrayList<String> grocery_list){
        super(context,R.layout.wantlist_layout,grocery_list);
        this.context = context;
        this.grocery_list = grocery_list;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       if(convertView == null){
           LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

           convertView = layoutInflater.inflate(R.layout.wantlist_layout,null);

           TextView count_item = convertView.findViewById(R.id.count_item);
           count_item.setText(position+1+".");
           TextView  name = convertView.findViewById(R.id.item_name);
           name.setText(grocery_list.get(position));

           ImageView delete_item = convertView.findViewById(R.id.remove_item);

           delete_item.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   add_grocery.remove_item(position);
               }
           });

       }



        return convertView;
    }
}
