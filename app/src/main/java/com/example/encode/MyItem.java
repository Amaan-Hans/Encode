package com.example.encode;

public class MyItem {
    private String username;
    private String question;
    private int likeCount;

    private int ID;

    public MyItem(String username, String question, int likeCount, int id) {
        this.username = username;
        this.question = question;
        this.likeCount = likeCount;
        this.ID = id;
    }

    public String getUsername() {
        return username;
    }

    public String getQuestion() {
        return question;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getid() {
        return ID;
    }
}

