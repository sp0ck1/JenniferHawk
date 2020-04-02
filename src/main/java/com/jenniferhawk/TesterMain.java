package com.jenniferhawk;

import com.github.twitch4j.helix.domain.User;
import com.github.twitch4j.helix.domain.UserList;
import com.github.twitch4j.tmi.domain.Chatters;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TesterMain {
    public static void main(String[] args) throws LoginException, InterruptedException {
        new Bot();
        Chatters chatters = Bot.twitchClient.getMessagingInterface().getChatters("Andy").execute();
        List<String> viewers = chatters.getAllViewers();
        UserList users = Bot.twitchClient.getHelix().getUsers(Bot.configuration.getCredentials().get("irc"),null,viewers).execute();
        for (int i = 0; i < users.getUsers().size(); i++) {
            System.out.println(users.getUsers().get(i));
        }
    }
}
