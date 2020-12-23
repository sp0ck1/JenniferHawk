package com.jenniferhawk.tests;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.helix.domain.*;
import com.jenniferhawk.Bot;
import org.junit.Test;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.jenniferhawk.Bot.twitchClient;

public class StreamListTest {

    @Test
    public void mallamaceStreamListTest() throws LoginException, InterruptedException {

        Bot bot = new Bot();
        bot.start();
        List<String> userList = new ArrayList<String>();

        userList.add("sp0ck1");

        boolean isLive = false;
        StreamList resultList = twitchClient.getHelix().getStreams(
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

        UserList resultList = twitchClient
                .getHelix()
                .getUsers(
                        Bot.OAUTH,
                        null,
                        Collections.singletonList("sp0ck1"))
                .execute();
    }

    @Test
    public void followListExample() throws LoginException, InterruptedException {
        Bot bot = new Bot();
        bot.start();
        bot.registerFeatures();

//        // To get everyone sp0ck1 follows...
//        UserList userList = twitchClient.getHelix().getUsers(Bot.OAUTH,null, Collections.singletonList("sp0ck1")).execute();
//        String userID = userList.getUsers().get(0).getId();
//        FollowList followList = twitchClient.getHelix().getFollowers(Bot.OAUTH,userID,null,null,100).execute();
//        List<Follow> follows = followList.getFollows();
//        for (Follow follower : follows) {
//            System.out.println(follower.getToName());
//        }

        // To get everyone that follows sp0ck1...
        UserList userList = twitchClient.getHelix().getUsers(Bot.OAUTH,null, Collections.singletonList("sp0ck1")).execute();
        String userID = userList.getUsers().get(0).getId();

        FollowList followList = twitchClient.getHelix().getFollowers(Bot.OAUTH,null,userID,null,100).execute();
        List<Follow> follows = followList.getFollows();
        for (Follow follower : follows) {
            System.out.println(follower.getFromName());
        }

    }
}
