package com.jenniferhawk.features;

import com.jenniferhawk.Bot;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.http.MediaType;

public class UpdateChannelStatus {

    static String KRAKEN = "https://api.twitch.tv/kraken/channels/";

    static HttpResponse<JsonNode> response;

    public static void updateTitle(String oAuth, String clientId, String channelId, String status) {

            try {

                response = Unirest.put(KRAKEN+channelId)
                        .header("accept", "application/vnd.twitchtv.v5+json; charset=UTF-8")
                        //.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                       // .header("user-agent","user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                        .header("authorization",oAuth)
                        .header("client-id",clientId)


                        .asJson();
            } catch (
                    UnirestException e) {
                e.printStackTrace();
                e.getMessage();
            }
            System.out.println(oAuth);
            System.out.println(response.getBody());
        }
    }
