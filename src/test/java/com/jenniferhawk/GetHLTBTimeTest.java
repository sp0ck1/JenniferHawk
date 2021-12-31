package com.jenniferhawk;

import com.jenniferhawk.howlongtobeat.HLTBEntry;
import com.jenniferhawk.howlongtobeat.HLTBTestLookup;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.junit.Test;

import static com.jenniferhawk.Bot.discordClient;

import javax.security.auth.login.LoginException;

public class GetHLTBTimeTest {

    @Test
    public void printTimes() throws LoginException, InterruptedException {

        Bot bot = new Bot();
        bot.start();


            MessageChannel channel = discordClient.getTextChannelById(673447079552483358L);

            HLTBEntry entry = HLTBTestLookup.searchGame("Madden 99");
            if (entry != null) {
                String game = entry.getName();
                String checkHLTB = !entry.getMainStoryTime().equals("0") ? " HLTB says " + entry.getName() + " takes " + entry.getMainStoryTime() + " to beat the main story." : "";
                channel.sendMessage(
                        "chatter" +
                                ", you are responsible for suggesting " +
                                game +
                                ". " + checkHLTB)
                        .queue();
            } else {
                channel.sendMessage("chatter" + ", you are responsible for suggesting " + entry.getName() + ". " + ". ").queue();
            }

    }
}




