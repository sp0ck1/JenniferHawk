package com.JenniferHawk.features;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.netflix.hystrix.HystrixCommandMetrics;
import org.apache.commons.lang3.StringUtils;
import static com.JenniferHawk.Bot.twitchClient;


import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Random;


public class TwitchCommands {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, YYYY h:mm z");

//   TwitchClient twitchClient = new com.github.twitch4j.TwitchClient(TwitchHelix helix);
    /**
     * Register events of this class with the EventManager
     *
     * @param eventManager EventManager
     */

    public TwitchCommands(EventManager eventManager) {
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }




    /**
     * Subscribe to the ChannelMessage Event and write the output to the console
     */

    private void onChannelMessage(ChannelMessageEvent event) throws ArrayIndexOutOfBoundsException {
        //Initializing a variable with word[1] or higher causes a fatal error if message is shorter than two words.
   //     TimerToggle timerToggle = new TimerToggle();

        String Author = String.format("%s", event.getUser().getName());
        char bang = '!';
        String original = event.getMessage();
        String[] word = StringUtils.split(original, "!, ");
        String[] phrase = original.split(" ", 3);
        int splitLength = phrase.length;
        String whereClause = word[0];  //Return first word, WITHOUT bang.
        String message = "";
        Random random = new Random();
        String messagein = word[0].toLowerCase();
        boolean isCommand = original.charAt(0) == bang;
        boolean isSp0ck1 = (event.getUser().getName().equals("sp0ck1"));

            if ( (isCommand) && (messagein.equals("time")) && (event.getChannel().getName().equals("sp0ck1")) ) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String time = sdf.format(timestamp);
                message = "It's " + time + " for sp0ck1.";
                event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
            }
            else if ((event.getUser().getName().equals("maikachan")) && (random.nextInt(5) == 3)) {
                message = "Thanks, Maika! TehePelo";
                event.getTwitchChat().sendMessage("sg4e", message);
            }
            else if ((original.equals("Daro stop"))) {
                message = "Daro please don't eat your wet shoe.";
                event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
            }
            else if ((messagein.equals("insert")) && (splitLength > 2)) {
                String Text = phrase[2];
                String Command = phrase[1];
                if (JenDB.checkQuery(Command) && !Author.equals("sp0ck1") ) {
                    event.getTwitchChat().sendMessage(event.getChannel().getName(), "You're not allowed to change that one!"); }
                else {
                message = "I set the command! ...Probably TehePelo";
                JenDB.addToHer(Command, Text, Author);
                event.getTwitchChat().sendMessage(event.getChannel().getName(), message);}
            }
            else if ((messagein.equals("insert"))) {
                message = "To add a command to my repertoire, use the sequence !insert [command] [text] (without brackets).";
                event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
            }
            else if ((isSp0ck1) && (messagein.equals("clear")) && (splitLength > 1)) {
                JenDB.deleteFromHer(word[1]);
                message = "I deleted it, I think!";
                event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
            }
            else if ( (isSp0ck1) && (messagein.equals("clear")) && (splitLength < 2)) {
                message = "Clear what?";
                event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
            }
            else if ( (isSp0ck1) && messagein.equals("timeron")) {

      //          timerToggle.TimerOn();
twitchClient.getChat().sendPrivateMessage("sp0ck1","A whisper from JenniferHawk");
               // twitchClient.getChat().sendMessage("sp0ck1","Timed Commands method active!");
                }
            else if ( (isSp0ck1) && messagein.equals("timeroff")) {

      //          timerToggle.TimerOff();
            } else if  (messagein.equals("uptime")) {
           //     timerToggle.sendUptime();
               // System.out.println(chatters);
                event.getTwitchChat().sendMessage(event.getChannel().getName(),"uptime");
            }
            else if (messagein.equals("poke") ) {
      //          timerToggle.sendPokeFact();

                System.out.println(event.getPermissions());
            }
            else if (messagein.equals("25") && event.getChannel().getName().equals("sg4e")) { //
      //      timerToggle.send24();
            }

            else if (isCommand) {
                message = JenDB.queryHer(whereClause);
                event.getTwitchChat().sendMessage(event.getChannel().getName(), message);

            }


        }

}



//    TODO: Get current game
//    public void getGames() {
//        // TestCase
//        GameList resultList = testUtils.getTwitchHelixClient().getGames(Arrays.asList(overwatchGameId), null).execute();
//    }
//    public void getGame() {
//    Game gameResult = getGame();
/** Example code for sending a message */
//                String message = String.format(
//                "Channel: %s / %s: %s",
//                event.getChannel().getName(),
//                event.getUser().getName(),
//                event.getMessage()
        // event.getTwitchChat().sendMessage(event.getChannel().getName(), message)