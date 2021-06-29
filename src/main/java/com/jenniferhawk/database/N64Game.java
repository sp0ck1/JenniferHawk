package com.jenniferhawk.database;


import com.jenniferhawk.speedrunslive.SRLComment;
import com.jenniferhawk.speedrunslive.SRLObjectMapper;
import com.jenniferhawk.speedrunslive.SRLRaceResultEntry;
import com.jenniferhawk.speedrunslive.SRLResultList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    private String srlURL;
    private String srlRaceID;

    public String getSrlURL() {
        return srlURL;
    }

    public void setSrlURL(String srlURL) {
        if (srlURL != null && srlURL.length() > 6) {
            setSrlRaceID(srlURL);
        }
        this.srlURL = srlURL;
        System.out.println("srlURL : " + srlURL);
    }

    private void setSrlRaceID(String srlURL) {

        srlURL = srlURL.substring(srlURL.length() - 6);

        this.srlRaceID = srlURL;
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

    public SRLComment getRandomComment() throws IOException {
        ArrayList<SRLComment> commentList = new ArrayList<>();

        if (srlURL != null) {
            List<SRLRaceResultEntry> entries = new SRLObjectMapper().mapRaceResultJSON(srlRaceID).getEntries();
            entries.forEach(srlRaceResultEntry -> {
                if (!srlRaceResultEntry.getComment().equals("")) {
                    SRLComment newComment = new SRLComment(title, srlRaceResultEntry.getComment(), srlRaceResultEntry.getPlayer());
                    commentList.add(newComment);
                }
            });

            // commentList.size = 0 means no comments
            // commentList.size = 1 means 1 comment
            // commentList.get(random.nextInt(0)) would return the first comment
            if (commentList.size() != 0) {
                Random random = new Random();
                // This would always get at least 1, and never 0.
                // But if it's not + 1, it won't ever get the last entry, either.
                // No, if commentList.size = 2, this will get random.nextInt(3), which could still get 0.
                SRLComment randomComment = commentList.get(random.nextInt(commentList.size()));

                return randomComment;
            } else return null;
        } else return null;

    }
}