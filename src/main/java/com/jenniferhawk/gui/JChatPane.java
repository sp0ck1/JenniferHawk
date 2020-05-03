package com.jenniferhawk.gui;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.jenniferhawk.Bot.twitchClient;

public class JChatPane extends JPanel {

    private static JTextPane ta = new JTextPane();

    private static Map<String,Color> usernameColors = new HashMap<>();
    private JTextField chatEntry = new JTextField();
    private static JScrollPane scrollPane;
    // A JChatPane prints its width and height on resize.
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            System.out.println("W:" + getSize().width + ", H:" + getSize().height);

        }

    public void initChat(EventManager eventManager) {
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }

    public void onChannelMessage(ChannelMessageEvent event) {

        //appendText(event.getUser().getName() + " says: " + event.getMessage());
        appendMessage(event.getUser().getName(), event.getMessage());
    }

    public static void setNewColor(String username) {
            Random random = new Random();
            Color newColor = new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
            usernameColors.put(username,newColor);
    }

    public JChatPane(String buttonOne, String buttonTwo) {
        super(new BorderLayout());
        Dimension chatWindow = new Dimension(400,800);
        setPreferredSize(chatWindow);
        setMinimumSize(chatWindow);
        JPanel panel = new JPanel();
        add(panel);

        panel.setLayout(new GridBagLayout());

        JButton saveButton = new JButton(buttonOne);
        JButton closeButton = new JButton(buttonTwo);

        GridBagConstraints c = new GridBagConstraints();



        ta.setEditable(false);
        //ta.setBorder(BorderFactory.createTitledBorder("Text Area"));
        //ta.setBorder(BorderFactory.createBevelBorder(1));
        ta.setBackground(TwitchColors.DARK_MODE_BACKGROUND);
        scrollPane = new JScrollPane(ta);

        scrollPane.setPreferredSize(new Dimension(420,700));
        setMinimumSize(new Dimension(420,700));
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                        "Chat window"),BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
        scrollPane.add(new JButton("scrollbutton"));
        c.fill = GridBagConstraints.VERTICAL;
        c.weighty = 1;
        c.weightx = 1;
        c.gridy = 0;
        panel.add(scrollPane,c);

        c.gridy = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        panel.add(chatEntry,c);

        c.gridy = 2;
        panel.add(saveButton,c);
        c.gridy = 3;
        panel.add(closeButton,c);

        scrollPane.getHorizontalScrollBar().setEnabled(false);
        chatEntry.addKeyListener(new ChatEntryListener());
        saveButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        add(scrollPane,BorderLayout.NORTH);
        add(panel,BorderLayout.SOUTH);
        setVisible(true);
        setOpaque(true);


    }

    public static void appendText(String msg)  {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground, Color.WHITE);


        aset = sc.addAttribute(aset, StyleConstants.Background, TwitchColors.DARK_MODE_BACKGROUND);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Arial");
        aset = sc.addAttribute(aset, StyleConstants.Alignment,
                StyleConstants.ALIGN_JUSTIFIED);
        aset = sc.addAttribute(aset, StyleConstants.FontSize,15);

        int len = ta.getDocument().getLength();
        ta.setCaretPosition(len);

        StyledDocument doc = ta.getStyledDocument();

        try {
            doc.insertString(doc.getLength(), msg+"\n", aset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
        ta.setCaretPosition(ta.getDocument().getLength());
    }

    public void appendMessage(String username,String message) {

        Random random = new Random();

        Color userColor;

        if (usernameColors.get(username) == null) {
            Color newUserColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            usernameColors.put(username, newUserColor);
        }
        userColor = usernameColors.get(username);

        SimpleAttributeSet messageAttributes = new SimpleAttributeSet();
        messageAttributes.addAttribute(StyleConstants.Foreground, userColor);


        messageAttributes.addAttribute(StyleConstants.Background, TwitchColors.DARK_MODE_BACKGROUND);
        StyleConstants.setBold(messageAttributes,true);
        messageAttributes.addAttribute(StyleConstants.FontFamily, "Arial");
        messageAttributes.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        messageAttributes.addAttribute(StyleConstants.FontSize,15);

        int len = ta.getDocument().getLength();
        ta.setCaretPosition(len);

        StyledDocument doc = ta.getStyledDocument();

        try {
            doc.insertString(doc.getLength(), username, messageAttributes);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        messageAttributes.removeAttribute(userColor);
        messageAttributes.addAttribute(StyleConstants.Foreground, Color.WHITE);
        StyleConstants.setBold(messageAttributes,false);

        try {
            doc.insertString(doc.getLength(), ": " + message+"\n", messageAttributes);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }


        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
        ta.setCaretPosition(ta.getDocument().getLength());

    }

    public void appendError(String msg) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground, Color.RED);

        aset = sc.addAttribute(aset, StyleConstants.Background, Color.WHITE);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment,
                StyleConstants.ALIGN_JUSTIFIED);

        int len = ta.getDocument().getLength();
        ta.setCaretPosition(len);

        StyledDocument doc = ta.getStyledDocument();
        try {
            doc.insertString(doc.getLength(),msg+"\n" , aset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private class ChatEntryListener extends KeyAdapter {

        /**
         * Invoked when a key has been pressed.
         * See the class description for {@link KeyEvent} for a definition of
         * a key pressed event.
         *
         * @param e the event to be processed
         */
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ENTER) {
                String message = chatEntry.getText();
                twitchClient.getChat().sendMessage("Sp0ck1",message);
                chatEntry.setText("");
            }
        }

    }
}


//        c.gridheight = 399;
//        c.gridwidth = 399;
//        c.ipady = 399;
//        c.ipadx = 399;
//        c.weighty = 1;
//        c.weightx = 1;