package com.jenniferhawk.database;


public class N64Game {

    private String title;
    private String publisher;
    private int year;
    private String genre;
    private String id;
    private String developer;

    public String getTitle() {
        return title;
    }

    public String getDeveloper() {
        return developer;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getId() {
        return id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setYear(int year){this.year = year;}

    public void setGenre(String genre){this.genre = genre;}

    public void setId(String id){this.id = id;}

}