package com.jenniferhawk.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.jenniferhawk.database.JenDB;
import com.jenniferhawk.database.N64Game;
import com.jenniferhawk.features.TimerToggle;

import java.awt.Dimension;

import static com.jenniferhawk.Bot.twitchClient;

public class ButtonPanel extends JPanel implements ActionListener {

    private TimerToggle timer = new TimerToggle();
    private JTextArea messageEntry = new JTextArea();
    private JTextArea channelName = new JTextArea();


    public ButtonPanel() {
        super(new GridLayout(4,2));
        //setBorder(BorderFactory.createTitledBorder("Press Jennifer's Buttons"));
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                        "Press Jennifer's Buttons")));
        setPreferredSize(new Dimension(400, 800));

//        JTable table = new JTable(new DefaultTableModel(new Object[]{"Column1", "Column2"}, 20));
//        DefaultTableModel model = (DefaultTableModel) table.getModel();
//        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
//        frame.add(table);


        JButton button,button1, button2, button3,button4, button5, textButton, byeSG;
        button = new JButton("Timer On");
        button1 = new JButton("Timer Off");
        button2 = new JButton();
        button3 = new JButton("Poke Facts");
        button4 = new JButton("Poke Stop");
        button5 = new JButton("Pyramid");
        textButton = new JButton("Send A Message");
        byeSG = new JButton("Close Jennifer");
        add(button);
        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(button5);
        add(textButton);
        add(byeSG);
        button.addActionListener(this);
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);
        button5.addActionListener(this);
        textButton.addActionListener(this);
        byeSG.addActionListener(this);

        JenniferImages n64ManiaIcon = new JenniferImages();

        button2.setIcon(n64ManiaIcon.getN64ManiaIcon());

        setVisible(true);

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
                N64Game n64Game = JenDB.rolln64();
                String game = n64Game.getTitle();
                String gameid = n64Game.getId();
                twitchClient.getChat().sendMessage("sp0ck1",

                    "I present to you: " +
                            game +
                            "! For more info, type !GameID " +
                            gameid);
                break;
            case "Poke Facts":
            case "Poke Stop":
                //   timer.pokeOn();
                System.out.println("PokeFacts are currently disabled due to changes in the PubSub Scheduler Service." +
                        " Fix ASAP or remove this button until redesign of the GUI.");
                break;
            //  timer.pokeStop();
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
               // twitchClient.getChat().sendMessage("sp0ck1","See you next time!");
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

}
