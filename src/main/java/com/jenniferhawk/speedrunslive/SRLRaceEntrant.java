package com.jenniferhawk.speedrunslive;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SRLRaceEntrant {

//    public Integer getRace() {
//        return race;
//    }
//
//    public void setRace(Integer race) {
//        this.race = race;
//    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String getPlayer() { return player; }

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

//    @JsonProperty("race")
//    Integer race;
    @JsonProperty("place")
    int place;
    @JsonProperty("playerName")
    String player;
    @JsonProperty("time")
    int time;
    @JsonProperty("message")
    String comment;

}
