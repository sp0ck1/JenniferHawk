package com.jenniferhawk.tests;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.helix.domain.Stream;
import com.github.twitch4j.helix.domain.StreamList;
import com.github.twitch4j.helix.domain.UserList;
import com.jenniferhawk.Bot;
import org.junit.Test;

import javax.security.auth.login.LoginException;
import java.util.Collections;

public class StreamListTest {

    @Test
    public void mallamaceStreamListTest() throws LoginException, InterruptedException {

        Bot bot = new Bot();
        bot.start();

        boolean isLive = false;
        StreamList resultList = Bot.twitchClient.getHelix().getStreams(
                Bot.OAUTH, "", "", null, null, null, null, null,
                Collections.singletonList("sp0ck1")).execute();
        if (resultList.getStreams().size() != 0) {
            for (Stream stream : resultList.getStreams()) {
                isLive = stream.getType().equals("live");
                System.out.println("Are  they live? Answer: " + isLive);
            }
        }

        System.out.println(isLive);
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
