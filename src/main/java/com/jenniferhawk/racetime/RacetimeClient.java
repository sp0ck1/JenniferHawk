package com.jenniferhawk.racetime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.NonNull;
import org.apache.commons.lang3.exception.ContextedRuntimeException;

import java.util.HashMap;

import static com.jenniferhawk.Bot.configuration;

public class RacetimeClient {

    boolean invitational = false;
    boolean unlisted = false;
    int start_delay = 15;
    int time_limit = 72;
    boolean streaming_required = false;
    boolean auto_start = true;
    boolean allow_comments = true;
    boolean allow_midrace_chat = true;
    boolean allow_nonentrant_chat = true;
    int chat_message_delay = 0;
    String n64maniaCategoryURL = "https://racetime.gg/o/n64mania/";
    OAuth2Credential oAuth2Credential;

    public RacetimeClient() {

    }

    @NonNull
    /**
     * @param gameName The name of the game to be raced, passed to the API as "custom_goal"
     * @param customGoal The goal of the game to be raced, passed to the API as "info"
     */
    public String startRace(String gameName, String customGoal) {

        HttpResponse<String> response = null;

        String custom_goal = gameName;
        String info = customGoal;

        if (!oAuth2Credential.getAccessToken().equals("")) {

            try {
                System.out.println("Using token: " + oAuth2Credential.getAccessToken() + "\n" + n64maniaCategoryURL + "startrace");
                response = Unirest.post(n64maniaCategoryURL + "startrace")
                        .header("Authorization", "Bearer " + oAuth2Credential.getAccessToken())
                        //.header("referer","https://racetime.gg/n64mania/startrace")
                        .field("custom_goal", custom_goal)
                        .field("info", info)
                        .field("invitational", invitational)
                        .field("unlisted", unlisted)
                        .field("start_delay", start_delay)
                        .field("time_limit", time_limit)
                        .field("streaming_required", streaming_required)
                        .field("auto_start", auto_start)
                        .field("allow_comments", allow_comments)
                        .field("allow_midrace_chat", allow_midrace_chat)
                        .field("allow_nonentrant_chat", allow_nonentrant_chat)
                        .field("chat_message_delay", chat_message_delay).asString();
            } catch (UnirestException e) {
                e.printStackTrace();
                throw new ContextedRuntimeException("Error when attempting to start race: " + custom_goal + " " + info, e);
            }

            System.out.println(response.getStatusText() + " " + response.getStatus() + " " + response.getBody());

        }

        if (response != null) {
            System.out.println(response.getHeaders().toString());
            return response.getHeaders().get("location").toString();

        } else return null;
    }

    public RacetimeClient start() {
        HttpResponse<String> response = null;

        try {
            response = Unirest.post("https://racetime.gg/o/token")
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("client_id",configuration.getRacetime().get("client_id"))
                    .field("client_secret",configuration.getRacetime().get("client_secret"))
                    .field("grant_type","client_credentials").asString();
        } catch (UnirestException unirestException) {
            unirestException.printStackTrace();
        }

        
        System.out.println("Token status is: " + (response != null ? response.getStatusText() : null) + " and " + response  .getStatus());
        // Add logging for status, if not other fields of the response variable
        try {
            System.out.println("Attempting to log access token. . .");
            if (response != null && response.getStatus() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                HashMap<String, Object> tokenInfo = objectMapper.readValue(response.getBody(), new TypeReference<HashMap<String, Object>>() {
                });
//                System.out.println("Access token is: " + tokenInfo.get("access_token")); // Confirmed works 9:30 PM 1/1/2022
                this.oAuth2Credential = new OAuth2Credential("racetime",tokenInfo.get("access_token").toString());
                System.out.println("Added " + oAuth2Credential.getAccessToken() + " to OAuth2Credential");
            }
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }

        return this;
    }
}
