package com.JenniferHawk.features;

import com.JenniferHawk.JenniferGUI.JChatPane;
import com.JenniferHawk.Layout.FileWriters;
import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Random;

import static java.lang.Integer.parseInt;

public class N64 {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, YYYY h:mm z");
    FileWriters writer = new FileWriters();
    /**
     * Register events of this class with the EventManager
     *
     * @param eventManager EventManager
     */

    public N64(EventManager eventManager) {
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(ChannelMessageEvent.class, event -> N64Commands(event));
    }

    /**
     * Subscribe to the ChannelMessage Event and listen for commands beginning with !n64mania
     */

    private void N64Commands(ChannelMessageEvent event) throws ArrayIndexOutOfBoundsException {

        //Initializing a variable with segments[1] or higher causes a fatal error if message is shorter than two words.
        String chatter = String.format("%s", event.getUser().getName()); //Name of chatter
        final char BANG = '!';
        String original = event.getMessage(); //Original message, preserving caps
        boolean isCommand = original.charAt(0)==BANG;
        String[] word = StringUtils.split(original, "!, "); //Separates string from bang and each word into its own spot in the array
        String[] phrase = original.split(" ", 3); // [0]=Word 1, [1]=Word 2, [2]=Rest of phrase
        int phraseLength = word.length; // Should return 1, 2, or 3
        String whereClause = word[0];  //Return first word, WITHOUT bang.
        String firstWord = whereClause.toLowerCase(); //Clean first word of any caps
        String message; // This variable is initialized here so that it can change based on different conditions
        Random random = new Random();
        String channel = event.getChannel().getName();

        CommandPermission modPerm = CommandPermission.MODERATOR;
        CommandPermission broadPerm = CommandPermission.BROADCASTER;
        boolean isModerator = event.getPermissions().contains(modPerm);
        boolean isBroadcaster = event.getPermissions().contains(broadPerm);

        //event.getPermissions() == perm;


        try {


            // -------------------------------------------------------------------------------------------- //
if (isCommand)
            switch (firstWord) {
                case "n64mania":
                    if (phraseLength > 1) {
                        if ("lookup".equals(word[1])) {
                            System.out.println("Looking up: " + phrase[2]);
                            String lookup = phrase[2];
                            String[] maybeGame = JenDB.N64Lookup(lookup);
                            if (maybeGame[1] == null) {
                                event.getTwitchChat().sendMessage(channel, "I'm not sure! Could you be a little more specific? Do you remember anything else?");
                                JChatPane.appendText("JenniferHawk: " + "I'm not sure! Could you be a little more specific? Do you remember anything else?");
                            } else {
                                event.getTwitchChat().sendMessage(channel, "Did you want " + maybeGame[1] + "? It's !GameID " + maybeGame[0] + ".");
                                JChatPane.appendText("JenniferHawk: " + "Did you want " + maybeGame[1] + "? It's !GameID " + maybeGame[0] + ".");
                            }
                        } // in case other !n64mania [word] commands are added, like checking first/second/third in race

                        if ( isBroadcaster || isModerator ) {
                                System.out.println("Chatter is sp0ck1. Phrase length is: " + phraseLength);
                                switch (word[1]) {
                                    case "link":
                                        String url = word[2];
                                        JenDB.N64UpdateCurrent(url, "URL");
                                        break;
                                    case "new":
                                        Integer gID = parseInt(word[2]);
                                        String current = JenDB.setNewN64Game(gID);
                                        event.getTwitchChat().sendMessage(channel, "Current game has been set to: " + current);
                                        JChatPane.appendText("JenniferHawk: " + "Current game has been set to: " + current);
                                        break;
                                    case "complete":
                                        JenDB.N64Complete();
                                        break;
                                    case "clear": // Clears layout and n64_current table

                                        try {
                                            writer.clearN64Layout();
                                        } catch (IOException e) {e.printStackTrace();}
                                        JenDB.N64Clear();
                                    case "first":
                                    case "second":
                                    case "third":
                                    case "fourth":
                                    case "fifth":
                                        String runner = word[2];
                                        String column = word[1].toUpperCase(); // example !n64mania first tapioca
                                        JenDB.N64UpdateCurrent(runner, column);
                                        break;
                                    case "clearlayout":
                                        //FileWriters writer = new FileWriters();
                                        writer.clearN64Placements();
                                        break;
                                } // End this switch for isBroadcaster
                                }} // End if (wordCount > 1)
                              else {
                                System.out.println("Regular n64mania called");
                                String[] current = JenDB.N64Current();
                                if (current[0] == null) {
                                    message = "This week's game hasn't been decided yet! Use !rolln64 to give us some suggestions.";
                                } else {
                                    message = "This week's N64Mania game is " + current[1] + ". The race starts around 9PM EST on Friday! Use !GameID " + current[0] + " for more info about the game.";
                                }
                                event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
                                JChatPane.appendText("JenniferHawk: " + message);
                            } //End if (wordCount = 1), continue switch (firstWord) {
                            break; // break n64mania case


                // Other N64 Related Commands


                case "rolln64":
                    String[] results = JenDB.rolln64();
                    String game = results[1];
                    String gameid = results[0];
                    event.getTwitchChat().sendMessage(channel,
                            chatter +
                                    ", your next N64 game is " +
                                    game +
                                    ". For more info, type !GameID " +
                                    gameid);
                    JChatPane.appendText("JenniferHawk: " + chatter +
                            ", your next N64 game is " +
                            game +
                            ". For more info, type !GameId " + gameid);
                    break;

                case "gameid":
                    int ID = parseInt(word[1]);
                    String[] info = JenDB.n64Info(ID);
                    event.getTwitchChat().sendMessage(channel,
                            info[0] +
                                    " was released in " + info[1] +
                                    ". "+ info[2] +
                                    " developed it and " + info[3] +
                                    " published it. It was released in " + info[4] +
                                    " . It's in the " + info[5] + " genre.");

                    JChatPane.appendText("JenniferHawk: " + info[0] +
                            " was released in " + info[1] +
                            ". "+ info[2] +
                            " developed it and " + info[3] +
                            " published it. It was released in " + info[4] +
                            " . It's in the " + info[5] + " genre.");
                    break;


                        }
                    } catch(ArrayIndexOutOfBoundsException | IOException a){
                    System.out.println("There are not enough words in the message to execute this command.");
                    a.getMessage();
                }
            }
        }

