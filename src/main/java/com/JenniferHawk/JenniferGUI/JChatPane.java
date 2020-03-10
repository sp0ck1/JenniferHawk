package com.JenniferHawk.JenniferGUI;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class JChatPane extends JPanel {

    private JTextPane ta = new JTextPane();
    private Color color = new Color(24,24,27); // Twitch Dark Mode color


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
        appendText(event.getUser().getName() + " says: " + event.getMessage());
    }



    public JChatPane(String buttonOne, String buttonTwo) {
        super(new BorderLayout());




        setPreferredSize(new Dimension(400, 800));

        JPanel panel = new JPanel();
        //panel.setLayout(new BorderLayout());
        panel.setLayout(new GridBagLayout());

        JButton saveButton = new JButton(buttonOne);
        JButton closeButton = new JButton(buttonTwo);

        GridBagConstraints c = new GridBagConstraints();



        ta.setEditable(false);
        ta.setBorder(BorderFactory.createTitledBorder("Text Area"));
        ta.setBorder(BorderFactory.createBevelBorder(1));
        ta.setBackground(color);
        JScrollPane scrollPane = new JScrollPane(ta);

        scrollPane.setPreferredSize(new Dimension(300,700));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Chat window"));
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;
        c.gridy = 0;

        panel.add(scrollPane,c);

        //ta.setSize(new Dimension(300, 399));

        //    c.fill = GridBagConstraints.BOTH;
        c.gridy = 1;
        panel.add(saveButton,c);
        c.gridy = 2;
        panel.add(closeButton,c);

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

    public void appendText(String msg)  {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground, Color.WHITE);


        aset = sc.addAttribute(aset, StyleConstants.Background, color);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Arial");
        aset = sc.addAttribute(aset, StyleConstants.Alignment,
                StyleConstants.ALIGN_JUSTIFIED);

        int len = ta.getDocument().getLength();
        ta.setCaretPosition(len);

        StyledDocument doc = ta.getStyledDocument();

        try {
            doc.insertString(doc.getLength(), msg+"\n", aset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
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
}


//        c.gridheight = 399;
//        c.gridwidth = 399;
//        c.ipady = 399;
//        c.ipadx = 399;
//        c.weighty = 1;
//        c.weightx = 1;