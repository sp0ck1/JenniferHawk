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

    public static int truckMoney = 0;

    public static void main(String[] args) throws LoginException, InterruptedException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, URISyntaxException, IOException, IrcException {


        Bot bot = new Bot();

        bot.registerFeatures();
        bot.start();

        
        Thread srlThread = new Thread( () -> {


            try {
                SRL.startBot();
            } catch (IOException | IrcException e) {
                e.printStackTrace();
            }
        });
        srlThread.setName("SRL Thread");
        srlThread.start();


    }



}
