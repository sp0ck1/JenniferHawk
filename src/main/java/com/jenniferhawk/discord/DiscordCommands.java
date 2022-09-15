package com.jenniferhawk.discord;

import com.jenniferhawk.database.JenDB;
import com.jenniferhawk.n64mania.N64Game;
import com.jenniferhawk.howlongtobeat.*;
import com.jenniferhawk.utils.HLTBLookup;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import java.util.*;

import static java.lang.Integer.parseInt;

public class DiscordCommands extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println(event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        System.out.println(event.getChannel().getIdLong());
        //Initializing a variable with segments[1] or higher causes a fatal error if message is shorter than two words.
        // String chatter = String.format("%s", event.getAuthor().getName()); //Name of chatter
        String chatter = String.format("%s", event.getAuthor().getName()) + "_7k"; // temporary 7k version. !!! IF THIS IS CHANGED, REMOVE one set of \\ from each preceding \u005f in jenQuips() !!!
        String original = event.getMessage().getContentDisplay(); //Original message, preserving caps\
        MessageChannel channel = event.getChannel();

        if (original.toLowerCase().startsWith("!rolln64")) {

            String[] argumentList;
            N64Game n64Game;
            if (original.contains("--")) {
                argumentList = original.split(" ");
                n64Game = JenDB.rolln64(argumentList); // only asking if it has an argument list, and if so it's split on spaces
                System.out.println(Arrays.stream(argumentList));
            } else n64Game = JenDB.rolln64();

            String game = n64Game.getTitle();

            System.out.println("I looked for " + game + " on HLTB.");

            /* Temporary Rework until HLTB API can be worked with */
            /*
            //  HLTBEntry entry = HLTBLookup.searchGame(game);

            if (entry != null && !entry.getMainStoryTime().equals("--")) {
                String checkHLTB = !entry.getMainStoryTime().equals("0") ? " HLTB says " + entry.getName() + " takes " + entry.getMainStoryTime() + " to beat the main story." : "";
                channel.sendMessage(
                        chatter +
                                ", you are responsible for suggesting " +
                                game +
                                ". " + jenQuip(game) + checkHLTB)
                        .queue();
            } else {**/

                channel.sendMessage(chatter + ", you are responsible for suggesting " + game + ". " + jenQuip(game) + ". ").queue();
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
        quipSet.add("I wouldn't have chosen that one, but to each their own.");
        quipSet.add("That's on you, bud.");
        quipSet.add("This looks like trash, but what do I know?");
        quipSet.add("Do you own that one?");
        quipSet.add("Do you really want that on your record?");
        quipSet.add("Have you checked to see if there's a Gamecube version instead?");
        quipSet.add("I guess that's what you wanted, right?");
        quipSet.add("This could be good, I guess?");
        quipSet.add("Spock rented this once. He said it was fine.");
        quipSet.add("May Blockbuster bless your soul.");
        quipSet.add("Eat your heart out, Mario.");
        quipSet.add("Somehow, this one slipped by Miyamoto.");
        quipSet.add("Fancy! Or terrible, depending upon the circumstances.");
        quipSet.add("Can Flant farm us on this one?");
        quipSet.add("\u00af\\\\\\\u005f\u0028\u30c4\u0029\\\u005f\u002f\u00af");
        quipSet.add("It's no Pilotwings but, I guess so.");
        quipSet.add("I pity the fool who rolled this game.");
        quipSet.add("You know it's totally okay if you want to reroll this one, right?");
        quipSet.add("It's been _at least_ a month since I thought about this game.");
        quipSet.add("It's been _at most_ six years since I last pondered this video game's existence.");
        quipSet.add("The beginning of this game, it's good. But that end? What an end!");
        quipSet.add("I think that this one is really only good in the midgame. Just my opinion.");


        if (game.equalsIgnoreCase("Pilotwings 64")) {
            return "That's a damn fine video game.";
        } else if (
            game.equalsIgnoreCase("Gauntlet Legends")) {
                return "@Hydromedia ";
            } else {

                return quipSet.get(random.nextInt(quipSet.size()));
                }
            }
        }