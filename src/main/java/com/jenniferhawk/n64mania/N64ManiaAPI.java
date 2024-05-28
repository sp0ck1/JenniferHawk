package com.jenniferhawk.n64mania;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jenniferhawk.commands.Command;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.HttpResponse;
import org.apache.commons.lang.StringEscapeUtils;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.http.MediaType;

import java.lang.reflect.Array;


public class N64ManiaAPI {
    String n64maniaUrl = "https://n64mania.com/";

    public String getRandomRaceURL() {
        return getAPIResult(n64maniaUrl + "api/races/random/url");
    }

    public String getRandomGameName() {
        return getAPIResult(n64maniaUrl + "api/games/unplayed/random");
    }

    public N64ManiaRunback getRunback() {
        String result = getAPIResult(n64maniaUrl + "api/races/runback");
        ObjectMapper objectMapper = new ObjectMapper();

        N64ManiaRunback n64ManiaRunback = null;
        try {
            n64ManiaRunback = objectMapper.readValue(result, N64ManiaRunback.class);
        } catch (JsonProcessingException j) {
            j.printStackTrace();
        }

        return n64ManiaRunback;
    }

    private String getAPIResult(String url) {
        HttpResponse<String> response;

        try {
            response = Unirest.get(url).asString();
            return StringEscapeUtils.unescapeHtml(response.getBody()); // Prevent special characters from being ASCII-ized
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getCommandResponse(String command) {
        ObjectMapper objectMapper = new ObjectMapper();

        String result = getAPIResult(n64maniaUrl + "api/commands/" + command);
        System.out.println("The result is" + result);
        if (result.equals("null")) {
            return "";
        } else {
            try {
                return objectMapper.readValue(result, Command.class).getText();
            } catch (JsonProcessingException j) {
                j.printStackTrace();
            }
        }
        return null;
    }

    public String sendUpdateRequest() {
        ObjectMapper objectMapper = new ObjectMapper();

        String result = getAPIResult(n64maniaUrl + "api/attempt-update");

        if (result.equals("null")) {
            return "";
        } else {
            try {
                N64ManiaUpdateResult n64ManiaUpdateResult = objectMapper.readValue(result, N64ManiaUpdateResult.class);
            } catch (JsonProcessingException j) {
                j.printStackTrace();
            }
        }
        return null;
    }
}
