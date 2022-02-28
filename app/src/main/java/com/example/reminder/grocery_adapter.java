package com.example.reminder;

import java.util.ArrayList;

public class grocery_adapter {
    private String title;
    private ArrayList<String> item_list;


   public grocery_adapter(){

   }

   public grocery_adapter(String title, ArrayList<String> item_list){
       this.title = title;
       this.item_list = item_list;
   }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getItem_list() {
        return item_list;
    }

    public void setItem_list(ArrayList<String> item_list) {
        this.item_list = item_list;
    }
}
