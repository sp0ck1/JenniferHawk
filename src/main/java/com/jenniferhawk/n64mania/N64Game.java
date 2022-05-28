package com.jenniferhawk.n64mania;


public class N64Game {

    private String title;
    private String publisher;
    private int year;
    private String genre;
    private String id;
    private String developer;
    private String region;
    private String winner;
    boolean completed;
    private String raceURL;

    public String getRaceURL() {
        return raceURL;
    }

    public void setRaceURL(String raceURL) {

        this.raceURL = raceURL;
        System.out.println("raceURL : " + raceURL);
    }


    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getWinner() {
        return winner;
    }

    public N64Game setWinner(String winner) {
        this.winner = winner;
        return this;
    }

    public String getRegion() {
        return region;
    }

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

    public N64Game setTitle(String title) {
        this.title = title;
        return this;
    }

    public N64Game setDeveloper(String developer) {
        this.developer = developer;
        return this;
    }

    public N64Game setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public N64Game setYear(int year) {
        this.year = year;
        return this;
    }

    public N64Game setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public N64Game setId(String id) {
        this.id = id;
        return this;
    }

    public N64Game setRegion(String region) {
        this.region = region;
        return this;
    }

}