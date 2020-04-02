package com.jenniferhawk.layout;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class FileWriters {

    File game = new File("D:\\JenniferUtils\\Game.txt");
    File year = new File("D:/JenniferUtils/Year.txt");
    File count = new File("D:/JenniferUtils/Number.txt");
    File gameid = new File("D:/JenniferUtils/GameID.txt");

    public void writeChatToFile(@NotNull String message) throws IOException {
        File chatlog = new File("D:/JenniferUtils/Chatlog.txt");
        if (!message.equals("")) {
            FileUtils.writeStringToFile(chatlog, message + "\r\n", "UTF-8", true);
        }
    }

    public void writeN64InfoToFile(int GameID, String Game, String Year, int Count) throws IOException, NullPointerException {

        System.out.println(gameid.getAbsolutePath());

        FileUtils.writeStringToFile(gameid, String.valueOf(GameID), "UTF-8", false);
        FileUtils.writeStringToFile(game, Game, "UTF-8", false);
        FileUtils.writeStringToFile(year, Year, "UTF-8", false);
        FileUtils.writeStringToFile(count, String.valueOf(Count), "UTF-8", false);
    }

    public void writeN64PlaceToFile(String Place, String Name) throws IOException, NullPointerException {
        String s = Place;
        File place = new File("D:/JenniferUtils/" + s + ".txt");
        FileUtils.writeStringToFile(place, Name, "UTF-8", false);

    }

    public void clearN64Layout() throws IOException {
        String s = "";
        for (int i = 0; i <= 5; i++) {
            System.out.println(i);
            if (i == 1) s = "First";
            else if (i == 2) s = "Second";
            else if (i == 3) s = "Third";
            else if (i == 4) s = "Fourth";
            else if (i == 5) s = "Fifth";
            writeN64PlaceToFile(s, "Unclaimed"); // write "Unclaimed" to file

            FileUtils.writeStringToFile(gameid, "", "UTF-8", false);
            FileUtils.writeStringToFile(game, "", "UTF-8", false);
            FileUtils.writeStringToFile(year, "", "UTF-8", false);
            FileUtils.writeStringToFile(count, "", "UTF-8", false);
        }
    }

    public void clearN64Placements() throws IOException {
        String s = "";
        for (int i = 0; i <= 5; i++) {
            if (i == 1) s = "First";
            else if (i == 2) s = "Second";
            else if (i == 3) s = "Third";
            else if (i == 4) s = "Fourth";
            else if (i == 5) s = "Fifth";
            writeN64PlaceToFile(s, "Unclaimed"); // write "Unclaimed" to file
        }
    }
}
