package com.jenniferhawk.gui;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SoundboardPanel extends JPanel {


    Player player;

    public SoundboardPanel() {
        try {
            FileInputStream stream = new FileInputStream(new File("path"));
            player = new Player(stream);
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
