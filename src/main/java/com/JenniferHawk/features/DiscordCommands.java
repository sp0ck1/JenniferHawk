package com.JenniferHawk.features;

import com.JenniferHawk.VimmParser.VimmN64;
import com.JenniferHawk.howlongtobeat.*;
import com.JenniferHawk.howlongtobeat.config.HowLongToBeatConfig;
import com.JenniferHawk.howlongtobeat.handler.HowLongToBeatHandler;
import com.mashape.unirest.http.Unirest;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class DiscordCommands extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println(event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());

        //Initializing a variable with segments[1] or higher causes a fatal error if message is shorter than two words.
        String chatter = String.format("%s", event.getAuthor().getName()); //Name of chatter
        String original = event.getMessage().getContentDisplay(); //Original message, preserving caps

        MessageChannel channel = event.getChannel();
        String[] word = StringUtils.split(original, "!, ");
        User author = event.getAuthor();

        if (original.toLowerCase().startsWith("!rolln64")) {
            String[] results = JenDB.rolln64();
            String game = results[1];




            System.out.println("I looked for " + game + " on HLTB.");

            HowLongToBeatService service = new HowLongToBeatServiceDefaultImpl();
            HowLongToBeatSearchResultPage result = new HowLongToBeatSearchResultPage(game, service.search(game).getHtmlFragment());

            if (result.getEntries().size() != 0) {
                HowLongToBeatEntry entry = result.getEntries().get(0);
                double HLTB = entry.getMainStory();
                String hltbLink = entry.getDetailLink();
                String gameImage = entry.getImageSource();
                String hltbGame = entry.getName();
                System.out.println("Time: " + HLTB);
                System.out.println("Link: " + hltbLink);
                System.out.println("Image link: " + gameImage);

                EmbedBuilder embed = new EmbedBuilder();
                embed.setImage(gameImage);
                embed.setTitle("Cover Image");
                MessageEmbed builtEmbed = embed.build();
                System.out.println("Is this embed sendable? : " + builtEmbed.isSendable(AccountType.BOT));

                channel.sendMessage(
                        chatter +
                                ", you are responsible for suggesting " +
                                game +
                                ". " + jenQuip(game) + " HLTB says " + hltbGame + " takes " + HLTB + " hours to beat the main story. " + gameImage).queue(); // Add "This trash is from YEAR, published by PUBLISHER." If HLTB has an API, add "It takes HH:mm to beat on average"
            } else {
                channel.sendMessage(chatter + ", you are responsible for suggesting " + game + ". " + jenQuip(game) + ". ").queue();
            }
        }
        else if (original.toLowerCase().startsWith("!gameid"))
        {

            int ID = parseInt(word[1]);
            String[] info = JenDB.n64Info(ID);

            channel.sendMessage(
                    info[0] +
                            " was released in " + info[1] +
                            ". "+ info[2] +
                            " developed it and " + info[3] +
                            " published it. It was released in " + info[4] +
                            " . It's in the " + info[5] + " genre.").queue();
        }
    }

    private String jenQuip(String game) {
        Random random = new Random();
        if (game.equalsIgnoreCase("Pilotwings 64")) {
            return "That's a damn fine video game.";
        } else if (
            game.equalsIgnoreCase("Gauntlet Legends")) {
                return "@Hydromedia ";
            } else {

                switch (random.nextInt(7)) {
                    case 1: return "I wouldn't have chosen that one, but to each their own.";
                    case 2: return "That's on you, bud.";
                    case 3: return "This looks like trash, but what do I know?";
                    case 4: return "Do you really want that on your record?";
                    case 5: return "Do you own that one?";
                    case 6: return "Have you checked to see if there's a GameCube version instead?";
                }
                return "I guess that's what you wanted, right?";
            }
        }
    }





