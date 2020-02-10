package com.JenniferHawk;


import javax.security.auth.login.LoginException;
import javax.swing.*;

public class Launcher {

    public static void main(String[] args) throws LoginException, InterruptedException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Bot bot = new Bot();
        bot.registerFeatures();
        bot.start();
        // Load the GUI
        JenniferGUI GUI = new JenniferGUI();


    }



}
