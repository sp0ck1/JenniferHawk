package com.jenniferhawk.discord;

import com.jenniferhawk.database.JenDB;
import com.jenniferhawk.database.N64Game;
import com.jenniferhawk.howlongtobeat.*;
import com.jenniferhawk.utils.HLTBLookup;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;


import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.Integer.parseInt;

public class DiscordCommands extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println(event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        System.out.println(event.getChannel().getIdLong());
        //Initializing a variable with segments[1] or higher causes a fatal error if message is shorter than two words.
        // String chatter = String.format("%s", event.getAuthor().getName()); //Name of chatter
        String chatter = String.format("%s", event.getAuthor().getName()) + "_7k"; // temporary 7k version
        String original = event.getMessage().getContentDisplay(); //Original message, preserving caps
        System.out.println(original);
        MessageChannel channel = event.getChannel();
        String[] word = StringUtils.split(original, "!, ");
        User author = event.getAuthor();

        if (original.toLowerCase().startsWith("!rolln64")) {
            N64Game n64Game = JenDB.rolln64();
            String game = n64Game.getTitle();

            System.out.println("I looked for " + game + " on HLTB.");

            HLTBEntry entry = HLTBLookup.searchGame(game);

            if (entry != null && !entry.getMainStoryTime().equals("--")) {
                String checkHLTB = !entry.getMainStoryTime().equals("0") ? " HLTB says " + entry.getName() + " takes " + entry.getMainStoryTime() + " to beat the main story." : "";
                channel.sendMessage(
                        chatter +
                                ", you are responsible for suggesting " +
                                game +
                                ". " + jenQuip(game) + checkHLTB)
                        .queue();
            } else {
                channel.sendMessage(chatter + ", you are responsible for suggesting " + game + ". " + jenQuip(game) + ". ").queue();
            }
        }
        else if (original.toLowerCase().startsWith("!gameid"))
        {
//
//            int ID = parseInt(word[1]);
//            N64Game game = JenDB.getGameInfo(ID);
//            channel.sendMessage(
//                    game.getTitle() +
//                            " was released in " + game.getYear() +
//                            ". "+ game.getDeveloper() +
//                            " developed it and " + game.getPublisher() +
//                            " published it. It was released in " + game.getRegion() +
//                            " . It's in the " + game.getGenre() + " genre.").queue();
        }
    }
    // TODO: Change to take N64Game object in order to specify quips by genre.
    private String jenQuip(String game) {
        Random random = new Random();
        List<String> quipSet = new ArrayList<String>();
//        quipSet.add("I wouldn't have chosen that one, but to each their own.");
//        quipSet.add("That's on you, bud.");
//        quipSet.add("This looks like trash, but what do I know?");
//        quipSet.add("Do you own that one?");
//        quipSet.add("Do you really want that on your record?");
//        quipSet.add("Have you checked to see if there's a Gamecube version instead?");
//        quipSet.add("I guess that's what you wanted, right?");
//        quipSet.add("This could be good, I guess?");
//        quipSet.add("Spock rented this once. He said it was fine.");
//        quipSet.add("May Blockbuster bless your soul.");
//        quipSet.add("Eat your heart out, Mario.");
//        quipSet.add("Somehow, this one slipped by Miyamoto.");
//        quipSet.add("Fancy! Or terrible, depending upon the circumstances.");
//        quipSet.add("Can Flant farm us on this one?");
        quipSet.add("¯\\_(ツ)_/¯");
//        quipSet.add("It's no Pilotwings but, I guess so.");


        if (game.equalsIgnoreCase("Pilotwings 64")) {
            return "That's a damn fine video game.";
        } else if (
            game.equalsIgnoreCase("Gauntlet Legends")) {
                return "@Hydromedia ";
            } else {
            String quip = null;
            try {
                quip = new String(quipSet.get(random.nextInt(quipSet.size())).getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return quip;
                }
            }
        }