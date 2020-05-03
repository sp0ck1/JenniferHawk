package com.jenniferhawk.layout;

import com.github.twitch4j.helix.domain.Subscription;
import com.github.twitch4j.helix.domain.SubscriptionList;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static com.jenniferhawk.Bot.*;
import static com.jenniferhawk.Bot.BROADCASTER_ID;

public class Utils {

    private final File game = new File("D:\\JenniferUtils\\Game.txt");
    private final File year = new File("D:/JenniferUtils/Year.txt");
    private final File count = new File("D:/JenniferUtils/Number.txt");
    private final File gameid = new File("D:/JenniferUtils/GameID.txt");

    private static final File subCountFile = new File("D:/JenniferUtils/Subcount.txt");
    private static final File subPointsFile = new File("D:/JenniferUtils/Subpoints.txt");

    public void writeN64InfoToFile(int GameID, String Game, String Year, int Count) throws IOException, NullPointerException {

        FileUtils.writeStringToFile(gameid, String.valueOf(GameID), "UTF-8", false);
        FileUtils.writeStringToFile(game, Game, "UTF-8", false);
        FileUtils.writeStringToFile(year, Year, "UTF-8", false);
        FileUtils.writeStringToFile(count, String.valueOf(Count), "UTF-8", false);
    }

    public void writeN64PlaceToFile(String Place, String Name) throws IOException, NullPointerException {
        File place = new File("D:/JenniferUtils/" + Place + ".txt");
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

    public static void updateSubscriberInfo() {

        // Values begin at 0 and -3 to offset the Tier 3 sub broadcasters inherently receive to their own channel
        int subCount = 0;
        int subPoints = -3;

        SubscriptionList subList = twitchClient.getHelix().getSubscriptions(OAUTH, BROADCASTER_ID, null, null, 1).execute();
        String cursor = subList.getPagination().getCursor();

        // Because the broadcaster will always be a sub, there will always be at least one.
        // We count this tier because we can't determine the order that subscribers will come in from the request
        String firstSubTier = subList.getSubscriptions().get(0).getTier();

        subPoints = subPoints + getTierValue(firstSubTier);

        while (subList.getSubscriptions().size() != 0) {
            subList = twitchClient.getHelix().getSubscriptions(OAUTH, BROADCASTER_ID, cursor, null, 100).execute();
            cursor = subList.getPagination().getCursor();

            for (Subscription sub : subList.getSubscriptions()) {
               // String subscriberName = sub.getUserName();
                String subscriberTier = sub.getTier();
                subPoints = subPoints + getTierValue(subscriberTier);
                subCount++;
            }
        }

        try {
            FileUtils.writeStringToFile(subCountFile,String.valueOf(subCount),"UTF-8",false);
            FileUtils.writeStringToFile(subPointsFile,String.valueOf(subPoints),"UTF-8",false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static int getTierValue(String subscriberTier) {
        int value = 0;
        switch(subscriberTier) {
            case "1000": value = 1;
                break;
            case "2000": value = 2;
                break;
            case "3000": value = 3;
                break;
        }
        return value;
    }
}

