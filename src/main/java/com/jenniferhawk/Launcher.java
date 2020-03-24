package com.jenniferhawk;


import com.jenniferhawk.gui.JChatPane;
import com.jenniferhawk.gui.ButtonPanel;
import com.jenniferhawk.gui.StreamInfoPanel;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.net.URISyntaxException;

public class Launcher {

    public static void main(String[] args) throws LoginException, InterruptedException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, URISyntaxException {


        Bot bot = new Bot();


        // Load the GUI
        JChatPane chatPane = bot.createChatPane();
        ButtonPanel buttonPanel = new ButtonPanel();
        JFrame frame = new JFrame("JenniferHawk 2.0 WIP");
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        frame.setLayout(new BorderLayout());
        JPanel raisedPanel = new JPanel();
        StreamInfoPanel streamInfo = new StreamInfoPanel();

        raisedPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        raisedPanel.setLayout(gridBagLayout);

        raisedPanel.setMinimumSize(new Dimension(
                chatPane.getWidth() +
                buttonPanel.getWidth() +
                streamInfo.getWidth(),
                buttonPanel.getHeight()));

        chatPane.setBorder(BorderFactory.createRaisedBevelBorder());
        frame.getContentPane().add(raisedPanel);
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        raisedPanel.add(chatPane,c);
        c.gridx = 1;
        raisedPanel.add(buttonPanel,c);
        c.gridx = 2;
        raisedPanel.add(streamInfo,c);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        JChatPane.appendText("Welcome to JenniferHawk's panel!");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable exception) {
            // Fail silently.
        }
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        bot.registerFeatures();
        bot.start();



    }



}
