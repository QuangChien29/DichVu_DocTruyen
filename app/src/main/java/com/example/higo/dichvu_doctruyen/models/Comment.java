package com.example.higo.dichvu_doctruyen.models;

public class Comment {
    private String userID;
    private String content;
    private String like;

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getDislike() {
        return dislike;
    }

    public void setDislike(String dislike) {
        this.dislike = dislike;
    }

    private String dislike;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment() {

    }

    public Comment(String userID, String content) {

        this.userID = userID;
        this.content = content;
    }
}
