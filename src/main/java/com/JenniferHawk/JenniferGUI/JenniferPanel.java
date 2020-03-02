package com.JenniferHawk.JenniferGUI;/*
 * Copyright (c) 2000 David Flanagan. All rights reserved. This code is from the
 * book Java Examples in a Nutshell, 2nd Edition. It is provided AS-IS, WITHOUT
 * ANY WARRANTY either expressed or implied. You may study, use, and modify it
 * for any non-commercial purpose. You may distribute it non-commercially as
 * long as you retain this notice. For a commercial use license, or to purchase
 * the book (recommended), visit http://www.davidflanagan.com/javaexamples2.
 */

/*

FIRST_LINE_START	PAGE_START	FIRST_LINE_END

LINE_START	        CENTER	    LINE_END

LAST_LINE_START	    PAGE_END	LAST_LINE_END

 */

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class JenniferPanel extends JPanel {
    public JenniferPanel() {
        // Create and specify a layout manager
        this.setLayout(new GridBagLayout());

        // Create a constraints object, and specify some default values
        GridBagConstraints c = new GridBagConstraints();
       // c.fill = GridBagConstraints.HORIZONTAL; // components grow in both dimensions
        c.insets = new Insets(5, 5, 5, 5); // 5-pixel margins on all sides

        // Create and add a bunch of buttons, specifying different grid
        // position, and size for each.
        // Give the first button a resize weight of 1.0 and all others
        // a weight of 0.0. The first button will get all extra space.




        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1 ;
        c.weightx = c.weighty = 1.0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(80,0,0,0);
        this.add(new JButton("Button 1"), c);


        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = c.weighty = 0.0;
        c.anchor = GridBagConstraints.LINE_END;
//        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,0,0);
        this.add(new JButton("Browse..."), c);

        JTextField tField = new JTextField();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = GridBagConstraints.REMAINDER; ;
        c.gridheight = 1;
        c.ipadx = 200;
        c.weightx = c.weighty = 1.0;
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,0,75);
        this.add(tField, c);


//
//        c.gridx = 0;
//        c.gridy = 2;
//        c.gridwidth = 1;
//        c.gridheight = 1;
//        c.ipadx = 0;
//        c.weightx = c.weighty = 1.0;
//        c.anchor = GridBagConstraints.LAST_LINE_START;
//        c.insets = new Insets(0,0,80,0);
//        this.add(new JButton("Button 2"), c);
//
//
//        c.gridx = 0;
//        c.gridy = 4;
//        c.gridwidth = 1;
//        c.gridheight = 1;
//        c.weightx = c.weighty = 1.0;
//        this.add(new JButton("Button #5"), c);
//
//        c.gridx = 2;
//        c.gridy = 4;
//        c.gridwidth = 1;
//        c.gridheight = 1;
//        c.weightx = c.weighty = 1.0;
//        this.add(new JButton("Button #6"), c);
//
//        c.gridx = 3;
//        c.gridy = 4;
//        c.gridwidth = 2;
//        c.gridheight = 1;
//        c.weightx = c.weighty = 1.0;
//        this.add(new JButton("Button #7"), c);
//
//        c.gridx = 1;
//        c.gridy = 5;
//        c.gridwidth = 1;
//        c.gridheight = 1;
//        c.weightx = c.weighty = 0.0;
//        this.add(new JButton("Button #8"), c);
//
//        c.gridx = 3;
//        c.gridy = 5;
//        c.gridwidth = 1;
//        c.gridheight = 1;
//        c.weightx = c.weighty = 1.0;
//        this.add(new JButton("Button #9"), c);
    }
    public static void main(String[] a) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        JFrame f = new JFrame("JenniferGUI 2.0");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Dimension dimension = new Dimension(300,400);
        f.setPreferredSize(dimension);
        f.setContentPane(new JenniferPanel());
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
