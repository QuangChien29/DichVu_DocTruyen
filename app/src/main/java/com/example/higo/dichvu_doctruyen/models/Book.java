package com.example.higo.dichvu_doctruyen.models;

public class Book {
    private String name;
    private String author;
    private String sumChapter;
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {

        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {

        return id;
    }

    private String id;

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getImgURL() {

        return imgURL;
    }

    private String imgURL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSumChapter() {
        return sumChapter;
    }

    public void setSumChapter(String sumChapter) {
        this.sumChapter = sumChapter;
    }

    public Book() {

    }

    public Book(String name, String author, String sumChapter) {

        this.name = name;
        this.author = author;
        this.sumChapter = sumChapter;
    }
}
