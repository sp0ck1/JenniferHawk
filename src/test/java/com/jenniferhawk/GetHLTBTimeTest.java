package com.jenniferhawk;

import com.jenniferhawk.database.JenDB;
import com.jenniferhawk.database.N64Game;
import com.jenniferhawk.howlongtobeat.HLTBEntry;
import com.jenniferhawk.utils.HLTBLookup;
import org.junit.Test;

import javax.security.auth.login.LoginException;

public class GetHLTBTimeTest {

    @Test
    public void printTimes() throws LoginException, InterruptedException {

        Bot bot = new Bot();
        bot.start();

        for (int i = 1; i < 304; i++) {
            N64Game n64Game = JenDB.getGameName(i);
            HLTBEntry entry = HLTBLookup.searchGame(n64Game.getTitle());
            if (entry != null) {
                System.out.println(n64Game.getTitle() + " can be completed in " + entry.getMainStoryTime());
            } else System.out.println(n64Game.getTitle() + " does not have a time on HLTB");

        }
    }
}
