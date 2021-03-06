package com.jenniferhawk.speedrunslive;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SRLRaceResultEntry {

    public Integer getRace() {
        return race;
    }

    public void setRace(Integer race) {
        this.race = race;
    }

    @JsonProperty("race")
    Integer race;

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonProperty("place")
    int place;
    @JsonProperty("player")
    String player;
    @JsonProperty("time")
    int time;
    @JsonProperty("message")
    String comment;

}
