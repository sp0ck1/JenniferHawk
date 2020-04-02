package com.jenniferhawk.features;

import com.jenniferhawk.database.JenDB;
import com.jenniferhawk.gui.JChatPane;
import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.helix.domain.Game;
import com.github.twitch4j.helix.domain.GameList;
import com.github.twitch4j.helix.domain.StreamList;
import org.apache.commons.lang3.StringUtils;
import static com.jenniferhawk.Bot.twitchClient;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
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
        event.getChannel().getName();
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
                JChatPane.appendText("JenniferHawk: " + message);
            }
//            else if ((event.getUser().getName().equals("maikachan")) && (random.nextInt(5) == 3)) {
//                message = "Thanks, Maika! TehePelo";
//                event.getTwitchChat().sendMessage("sg4e", message);
//            }
            else if ((original.equals("Daro stop"))) {
                message = "Daro please don't eat your wet shoe.";
                event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
                JChatPane.appendText("JenniferHawk: " + message);
            }
            else if ((messagein.equals("set")) && (splitLength > 2)) {
                String Text = phrase[2];
                String Command = phrase[1];
                if (JenDB.checkQuery(Command) && !Author.equals("sp0ck1") ) {
                    event.getTwitchChat().sendMessage(event.getChannel().getName(), "You're not allowed to change that one!"); }
                else {
                message = "I set the command! ...Probably TehePelo";
                JenDB.addToHer(Command, Text, Author);
                event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
                    JChatPane.appendText("JenniferHawk: " + message);}
            }
            else if ((messagein.equals("set"))) {
                message = "To add a command to my repertoire, use the sequence !insert [command] [text] (without brackets).";
                event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
                JChatPane.appendText("JenniferHawk: " + message);

            }
            else if ((isSp0ck1) && (messagein.equals("clear")) && (splitLength > 1)) {
                JenDB.deleteFromHer(word[1]);
                message = "I deleted it, I think!";
                event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
                JChatPane.appendText("JenniferHawk: " + message);
            }
            else if (isSp0ck1 && messagein.equals("clear")) {
                message = "Clear what?";
                event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
                JChatPane.appendText("JenniferHawk: " + message);
            }
            else if ( (isSp0ck1) && messagein.equals("timeron")) {

      //          timerToggle.TimerOn();

               // twitchClient.getChat().sendMessage("sp0ck1","Timed Commands method active!");
                }
            else if ( (isSp0ck1) && messagein.equals("timeroff")) {

      //          timerToggle.TimerOff();
            } else if  (messagein.equals("uptime")) {
           //     timerToggle.sendUptime();
               // System.out.println(chatters);
                event.getTwitchChat().sendMessage(event.getChannel().getName(),"uptime");
                JChatPane.appendText("JenniferHawk: " + message);
            }
            else if (messagein.equals("poke") ) {
                TimerToggle.sendPokeFact();

            }
            else if (messagein.equals("newcolor")) {
                JChatPane.setNewColor(event.getUser().getName());
                event.getUser().getId();
            }
            else if (isCommand) {
                message = JenDB.queryHer(whereClause);
                event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
                if (!message.equals("")) {
                    JChatPane.appendText("JenniferHawk: " + message);
                }
            }


        }

        // Keep this method as an example of using StreamList, GameList, Game...
    public void send24() {
        List<String> sg4e = Collections.singletonList("sg4e");
        StreamList resultList = twitchClient.getHelix().getStreams("oauth:124tkcsi4od4062r3i9q63pjs5qwr5",null,  null, 1, null, null, null, null, Collections.singletonList("sg4e")).execute();
        resultList.getStreams().forEach(stream -> {
            GameList gameList = twitchClient.getHelix().getGames("oauth:124tkcsi4od4062r3i9q63pjs5qwr5", Collections.singletonList(stream.getGameId()),null).execute();
            Game game = gameList.getGames().get(0);
            String gameInfo = String.valueOf(game);
            String gameName = game.getName();// gameInfo.substring(gameInfo.indexOf("name=")+5,gameInfo.indexOf(", b"));
            Calendar j = stream.getStartedAt();

            String startTime = sdf.format(j.getTime());

            twitchClient.getChat().sendMessage("sg4e",
                    "Starting at "+startTime+", sg4e will be doing a 24 hour stream marathon. We'll start with everyone's favorite: "+gameName+"! " +
                            "Then we'll move on to YOUR Mario Maker 2 levels! Type !mm submit <level code> <comment> to add a level to the queue, " +
                            "use !queue to see what's next, and use !bully to get the latest information on whose records are being bopped. See you there! NoaGamba");
        });
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