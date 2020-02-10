package com.JenniferHawk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.JenniferHawk.features.JenDB;
import com.JenniferHawk.features.TimerToggle;
import java.awt.Dimension;

import static com.JenniferHawk.Bot.twitchClient;

public class JenniferGUI extends JFrame implements ActionListener {

    private TimerToggle timer = new TimerToggle();

    public JenniferGUI() {
        //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);



        JFrame frame = new JFrame("JenniferGUI");
        JButton button,button1, button2, button3,button4, button5, textButton, byeSG;
        button = new JButton("Timer On");
        button1 = new JButton("Timer Off");
        button2 = new JButton("Roll N64");
        button3 = new JButton("Poke Facts");
        button4 = new JButton("Poke Stop");
        button5 = new JButton("Pyramid");
        textButton = new JButton("Send A Message");
        byeSG = new JButton("Close Jennifer");
        frame.add(button);
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);
        frame.add(button4);
        frame.add(button5);
        frame.add(textButton);
        frame.add(byeSG);
        button.addActionListener(this);
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);
        button5.addActionListener(this);
        textButton.addActionListener(this);
        byeSG.addActionListener(this);
        frame.setLayout(new GridLayout(2,4));
        frame.setSize(600,300);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //button.setSize(,10);

          }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Timer On":
                timer.TimerOn();
                break;
            case "Timer Off":
                timer.TimerOff();
                break;
            case "Roll N64":
                String[] games = JenDB.rolln64();
                String game = games[1];
                String gameid = games[0];
                twitchClient.getChat().sendMessage("sp0ck1",

                    "I present to you: " +
                            game +
                            "! For more info, type !GameID " +
                            gameid);
                break;
            case "Poke Facts":
                timer.pokeOn();
                break;
            case "Poke Stop":
                timer.pokeStop();
                break;
            case "Pyramid":
                try {
                    JTextArea channelName = new JTextArea();
                    channelName.setEditable(true);
                    String channel = JOptionPane.showInputDialog(channelName,"Enter a channel name!","Channel:",JOptionPane.PLAIN_MESSAGE);
                    timer.pyramid(channel);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                break;
            case "Send A Message":
                JTextArea messageEntry = new JTextArea();
                JTextArea channelName = new JTextArea();
                messageEntry.setEditable(true);
                channelName.setEditable(true);
                JScrollPane scrollPane = new JScrollPane(messageEntry);
                scrollPane.requestFocus();
                messageEntry.requestFocusInWindow();
                scrollPane.setPreferredSize(new Dimension(800, 600));
                String c = JOptionPane.showInputDialog(channelName,"Enter a channel name!","Channel:",JOptionPane.PLAIN_MESSAGE);
                String s = JOptionPane.showInputDialog(messageEntry,"Send a message to a channel!","Enter text here!",JOptionPane.PLAIN_MESSAGE);

                //If a string was returned, say so.
                if ((s != null) && (s.length() > 0)) {
                    twitchClient.getChat().sendMessage(c, s);

                    System.out.println(s);

                }
                break;
            case "Bye, sg4e!":
                twitchClient.getChat().sendMessage("sg4e",

                        "See you next time, sg4e and maika!");
                break;
            case "Close Jennifer":
                System.out.println("I'm outta here!");
                twitchClient.getChat().sendMessage("sp0ck1","See you next time!");
                // Add something that closes the JFrame immediately
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
                break;
        }
        }


    public void myMethod() {
        JOptionPane.showMessageDialog(this, "Hello, World!");
    }
}
