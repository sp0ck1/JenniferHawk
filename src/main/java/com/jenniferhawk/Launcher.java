package com.jenniferhawk;

import com.jenniferhawk.irc.IRCBot;
import com.jenniferhawk.messages.ScheduledMessages;
import org.pircbotx.exception.IrcException;
import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static com.jenniferhawk.irc.IRCBot.SRL;

public class Launcher {

    public static void main(String[] args) throws LoginException, InterruptedException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, URISyntaxException, IOException, IrcException {


        Bot bot = new Bot();

        bot.registerFeatures();
        bot.start();

    //    SRL.startBot();
        ScheduledMessages scheduledMessages = new ScheduledMessages();

    }



}
