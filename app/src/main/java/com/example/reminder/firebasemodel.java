package com.example.reminder;

public class firebasemodel {


    private String Title;
    private String Content;

    public firebasemodel(){

    }

    public firebasemodel(String Title, String Content){

        this.Title = Title;
        this.Content = Content;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }
}


