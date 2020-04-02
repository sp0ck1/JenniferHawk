package com.jenniferhawk.features;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.jenniferhawk.Bot.twitchClient;

public class RandomCutie {

    boolean justSentRandomCutie;
    Stopwatch stopwatch = new Stopwatch();
    Random random = new Random();

    public RandomCutie(EventManager eventManager) {
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(ChannelMessageEvent.class, this::onRandomCutie);
    }

    public void onRandomCutie(ChannelMessageEvent event) {
        if(event.getChannel().getName().toLowerCase().equals("sp0ck1") && event.getMessage().toLowerCase().equals("!randomcutie")) {

            if (stopwatch != null) {
                System.out.println("Time since last !randomcutie: " + stopwatch.elapsedTime());
                if (stopwatch.elapsedTime() <= 30) {
                    twitchClient.getChat().sendMessage("SG4E", "!randomcutie" + " " + random.nextInt(255)); // Ask Maika to send a !randomcutie


                } else {
                    twitchClient.getChat().sendMessage("SG4E", "!randomcutie");
                }
                justSentRandomCutie = true;
                stopwatch.reset();
                System.out.println("Got a random cutie event");

            }
        }

        if(justSentRandomCutie && event.getUser().getName().toLowerCase().equals("maikachan")) {
            twitchClient.getChat().sendMessage("sp0ck1",event.getMessage());
            justSentRandomCutie = false;
            System.out.println("Sent a random cutie");
        }


    }
}
