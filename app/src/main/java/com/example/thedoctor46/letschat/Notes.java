package com.example.thedoctor46.letschat;



public class Notes {
   public String userId;
   public String title;
   public String note;

    public Notes()
    {

    }

    public Notes(String userId,String title, String note) {
        this.title = title;
        this.note = note;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }
}
