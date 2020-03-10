package com.JenniferHawk.JenniferGUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("JenniferHawk 2.0 Main");

        //getContentPane().add(new JenniferGUI());
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridBagLayout);
        JChatPane novgorod = new JChatPane("Nizhny", "Novgorod");
        JChatPane russia = new JChatPane("Muscovy","Russia");
        c.gridx = 0;
        getContentPane().add(novgorod,c);
        c.gridx = 1;
        getContentPane().add(russia,c);
        setVisible(true);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(novgorod.getWidth()+russia.getWidth(),
                novgorod.getHeight()));
        setPreferredSize(new Dimension(600,700));
        setMaximumSize(new Dimension(800,900));
        pack();

    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
