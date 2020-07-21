package com.jenniferhawk.irc;

import com.jenniferhawk.Bot;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.ListenerAdapter;

import java.io.IOException;

public class IRCBot extends ListenerAdapter {



    public static PircBotX SRL;

    public IRCBot() throws IOException, IrcException {
        Configuration configuration = new Configuration.Builder()
                .setName("JenniferHawk")
                .setNickservPassword(Bot.configuration.getIRC().get("password"))
                .addServer("irc.speedrunslive.com")
                .addAutoJoinChannel("#speedrunslive")

                .buildConfiguration();

        SRL = new PircBotX(configuration);




    }
}
