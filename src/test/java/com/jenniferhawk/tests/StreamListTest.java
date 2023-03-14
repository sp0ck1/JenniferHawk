package com.jenniferhawk.tests;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.helix.domain.Stream;
import com.github.twitch4j.helix.domain.StreamList;
import com.github.twitch4j.helix.domain.UserList;
import com.jenniferhawk.Bot;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.Body;
import org.junit.jupiter.api.Test;
import com.mashape.unirest.http.HttpResponse;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StreamListTest {

    @Test
    public void mallamaceStreamListTest() throws LoginException, InterruptedException {

        Bot bot = new Bot();
        bot.start();
        List<String> userList = new ArrayList<String>();

        userList.add("sp0ck1");

        boolean isLive = false;
        StreamList resultList = Bot.twitchClient.getHelix().getStreams(
                Bot.OAUTH, "", "", null, null, null, null, null,
                userList).execute();
        if (resultList.getStreams().size() != 0) {
            for (Stream stream : resultList.getStreams()) {
                isLive = stream.getType().equals("live");
                System.out.println("Are  they live? Answer: " + isLive);
                System.out.println(stream.getUserName());
            }
        }

        System.out.println("List was empty but the method didn't throw an error");
    }

    @Test
    public void userListTest() throws LoginException, InterruptedException {

        Bot bot = new Bot();
        bot.start();

        UserList resultList = Bot.twitchClient
                .getHelix()
                .getUsers(
                        Bot.OAUTH,
                        null,
                        Collections.singletonList("sp0ck1"))
                .execute();
    }


    }

