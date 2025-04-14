package com.example.encode;

public class commentitem {
    private String username;
    private String comment;
    private int likeCount;

    private int commentid;

    public commentitem(String username, String question, int likeCount, int id) {
        this.username = username;
        this.comment = question;
        this.likeCount = likeCount;
        this.commentid = id;
    }

    public String getUsername() {
        return username;
    }

    public String getQuestion() {
        return comment;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getCommentid() { return  commentid; }
}