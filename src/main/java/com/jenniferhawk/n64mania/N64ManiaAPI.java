package com.jenniferhawk.n64mania;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class N64ManiaAPI {
    String n64maniaUrl = "https://n64mania.com/";

    public String getRandomRace() {
        return getAPIResult(n64maniaUrl + "api/races/random/url");
    }

    public String getRandomGameName() {
        return getAPIResult(n64maniaUrl + "api/games/unplayed/random");
    }

    private String getAPIResult(String url) {
        HttpResponse<String> response;
        try {
            response = Unirest.get(url).asString();
            return response.getBody();
        } catch (UnirestException e) {e.printStackTrace(); }

        return null;
    }


}
