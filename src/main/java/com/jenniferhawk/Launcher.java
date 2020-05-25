package com.jenniferhawk;

import org.pircbotx.exception.IrcException;
import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Launcher {

    public static void main(String[] args) throws LoginException, InterruptedException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, URISyntaxException, IOException, IrcException {


        Bot bot = new Bot();

        bot.registerFeatures();
        bot.start();

      //  IRCBot ircBot = new IRCBot();




    }



}
