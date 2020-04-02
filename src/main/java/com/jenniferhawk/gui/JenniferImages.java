package com.jenniferhawk.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class JenniferImages {

    ImageIcon n64ManiaIcon;

    JenniferImages() {
        setIcons();
    }

    public ImageIcon getN64ManiaIcon() {
        return n64ManiaIcon;
    }

    public void setIcons() {

        InputStream is = getClass().getClassLoader().getResourceAsStream("n64mania.png");
        BufferedImage image = null;

        try {
            image = ImageIO.read(is);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        
        this.n64ManiaIcon = new ImageIcon(image);
    }
}
