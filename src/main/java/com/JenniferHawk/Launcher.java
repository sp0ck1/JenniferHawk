package com.JenniferHawk;


import com.JenniferHawk.JenniferGUI.JChatPane;
import com.JenniferHawk.JenniferGUI.JenniferGUI;
import com.JenniferHawk.JenniferGUI.JenniferPanel;
import com.JenniferHawk.JenniferGUI.MainFrame;

import javax.security.auth.login.LoginException;
import javax.swing.*;

public class Launcher {

    public static void main(String[] args) throws LoginException, InterruptedException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Bot bot = new Bot();
        bot.registerFeatures();
        bot.start();

        // Load the GUI
        JChatPane pane = bot.createChatPane();
        JenniferGUI GUI = new JenniferGUI();
        JFrame frame = new JFrame("This is a chat pane");
        frame.getContentPane().add(pane);
        frame.pack();
        frame.setVisible(true);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable exception) {
            // Fail silently.
        }




    }



}
