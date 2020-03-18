package com.JenniferHawk;


import com.JenniferHawk.JenniferGUI.JChatPane;
import com.JenniferHawk.JenniferGUI.ButtonPanel;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Launcher {

    public static void main(String[] args) throws LoginException, InterruptedException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, URISyntaxException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Bot bot = new Bot();
        bot.registerFeatures();
        bot.start();




        // Load the GUI
        JChatPane chatPane = bot.createChatPane();
        ButtonPanel buttonPanel = new ButtonPanel();
        JFrame frame = new JFrame("This is Jennifer's frame");
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        frame.setLayout(new BorderLayout());
        JPanel raisedPanel = new JPanel();
        raisedPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        raisedPanel.setLayout(gridBagLayout);
        raisedPanel.setMinimumSize(new Dimension(chatPane.getWidth()+buttonPanel.getWidth(),buttonPanel.getHeight()));
        chatPane.setBorder(BorderFactory.createRaisedBevelBorder());
        frame.getContentPane().add(raisedPanel);
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        raisedPanel.add(chatPane,c);
        c.gridx = 1;
        raisedPanel.add(buttonPanel,c);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        chatPane.appendText("Welcome to JennierHawk's panel!");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable exception) {
            // Fail silently.
        }




    }



}
