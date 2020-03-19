package com.JenniferHawk.JenniferGUI;

import com.github.twitch4j.helix.domain.StreamList;
import com.netflix.hystrix.HystrixCommand;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;

import static com.JenniferHawk.Bot.configuration;
import static com.JenniferHawk.Bot.twitchClient;

public class StreamInfoPanel extends JPanel {
    GridBagLayout gridBagLayout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    JTextArea titleText = new JTextArea();
    JTextArea goLiveText = new JTextArea();
    JTextField categoryText = new JTextField(15);
    JLabel titleLabel = new JLabel("Stream title:");
    JLabel goLiveLabel = new JLabel("Go live notification:");
    JLabel categoryLabel = new JLabel("Game:");
    Insets labelInsets = new Insets(0,0,10,0);
    Insets defaultInsets = new Insets(0,0,0,0);


    public StreamInfoPanel() {
        setLayout(gridBagLayout);
        setPreferredSize(new Dimension(400,800));
        setVisible(true);
        setBorder(BorderFactory.createTitledBorder("Stream Info Panel"));

        // Did not work! FIXME!
        titleText.setMinimumSize(new Dimension(0,50));

        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.insets = labelInsets;
        add(titleLabel,c);

        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        titleText.setBorder(BorderFactory.createLoweredBevelBorder());
      //  c.insets = defaultInsets;
        add(titleText,c);

        c.gridy = 2;
      //  c.insets = labelInsets;
        add(goLiveLabel,c);

        c.gridy = 3;
        goLiveText.setBorder(BorderFactory.createLoweredBevelBorder());
        add(goLiveText,c);

        c.gridy = 4;
     //   c.insets = labelInsets;
        add(categoryLabel,c);

        c.gridy = 5;
    //    c.insets = defaultInsets;
        categoryText.setBorder(BorderFactory.createLoweredBevelBorder());
        add(categoryText,c);
    }




    private void updateTitle(String newTitle) {

    }

    private void updateGame(String game) {

    }

    private void updateGoLivc(String goLive) {

    }
}
