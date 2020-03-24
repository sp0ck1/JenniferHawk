package com.jenniferhawk.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.*;
import java.awt.*;

import static javax.swing.BorderFactory.createLoweredBevelBorder;
import static javax.swing.BorderFactory.createRaisedBevelBorder;

public class StreamInfoPanel extends JPanel {
    GridBagLayout gridBagLayout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    JTextArea titleText = new JTextArea();
    JTextField goLiveText = new JTextField();
    JTextField categoryText = new JTextField(15);
    JLabel titleLabel = new JLabel("Stream title:");
    JLabel goLiveLabel = new JLabel("Go live notification:");
    JLabel categoryLabel = new JLabel("Game:");
    Insets labelInsets = new Insets(0,0,10,0);
    Insets defaultInsets = new Insets(0,0,0,0);
    private JLabel titleRemaining = new JLabel("140 characters remaining.");
    private DefaultStyledDocument doc;
    Color textFieldColor = new Color(58,58,60);
    AttributeSet attributeSet;
    Font Tahoma = new Font("Tahoma",Font.PLAIN,11);
    JPanel innerPanel = new JPanel();
    Border titledBorder = BorderFactory.createTitledBorder(null,"Stream Info Panel", TitledBorder.LEFT,TitledBorder.TOP, Tahoma,Color.BLACK);
    private JLabel n64Icon = new JLabel();

    Border setBorder(String title){
        return BorderFactory.createTitledBorder(createRaisedBevelBorder(),title, TitledBorder.LEFT,TitledBorder.ABOVE_TOP, Tahoma,Color.BLACK);
    }



    public StreamInfoPanel() {
        super(new GridBagLayout());
        goLiveText.setBorder(new RoundedCornerBorder());
        goLiveText.setBackground(TwitchColors.DARK_MODE_FOREGROUND);
        goLiveText.setOpaque(false);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                        "Press Jennifer's Buttons")));


        System.out.println(javax.swing.UIManager.getDefaults().getFont("Label.font"));

        innerPanel.setBackground(TwitchColors.DARK_MODE_BACKGROUND);
        StyleContext sc = StyleContext.getDefaultStyleContext();
        attributeSet = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground, Color.WHITE);
        attributeSet = sc.addAttribute(attributeSet, StyleConstants.FontFamily,"Arial");

        innerPanel.setLayout(gridBagLayout);
        innerPanel.setPreferredSize(new Dimension(400,800));
        innerPanel.setVisible(true);



        // Set text box size
//        titleText.setMinimumSize(new Dimension(300,50));
//        titleText.setPreferredSize(new Dimension(300,75));
//        titleText.setMaximumSize(new Dimension(300,100));
        titleText.setRows(3);
        titleText.setLineWrap(true);
        titleText.setToolTipText("Set the title with this text box!");
        titleText.setBackground(textFieldColor);
        titleText.setForeground(Color.WHITE);
        titleText.setFont(Tahoma);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(Tahoma);

        // Set character limit. Twitch titles can be 140 characters in length
        doc = new DefaultStyledDocument();
        doc.setDocumentFilter(new DocumentSizeFilter(140));
        titleText.setDocument(doc);

        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.insets = labelInsets;
        innerPanel.add(titleLabel,c);

        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        titleText.setBorder(createLoweredBevelBorder());
      //  c.insets = defaultInsets;
        innerPanel.add(titleText,c);

        c.gridy = 2;
      //  c.insets = labelInsets;
        innerPanel.add(goLiveLabel,c);

        c.gridy = 3;

        innerPanel.add(goLiveText,c);

        c.gridy = 4;
     //   c.insets = labelInsets;
        innerPanel.add(categoryLabel,c);

        c.gridy = 5;
    //    c.insets = defaultInsets;
        categoryText.setBorder(createLoweredBevelBorder());
        innerPanel.setBorder(createLoweredBevelBorder());

        innerPanel.add(categoryText,c);

        c.fill = GridBagConstraints.BOTH;

        JenniferImages n64ManiaIcon = new JenniferImages();
        ImageIcon image = n64ManiaIcon.getN64ManiaIcon();
        n64Icon.setIcon(image);
//        c.gridx = 0;
//        c.gridy = 0;
//        c.anchor = GridBagConstraints.CENTER;
//        this.add(n64Icon,c);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        this.setBorder(titledBorder);
        this.add(innerPanel,c);
    }




    private void updateTitle(String newTitle) {

    }

    private void updateGame(String game) {

    }

    private void updateGoLive(String goLive) {

    }
}
