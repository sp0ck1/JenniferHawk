package com.jenniferhawk.features;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.SubscriptionEvent;
import com.github.twitch4j.common.events.channel.ChannelGoLiveEvent;
import com.jenniferhawk.gui.JChatPane;
import com.jenniferhawk.layout.Utils;
import net.dv8tion.jda.api.entities.TextChannel;

import static com.jenniferhawk.Bot.discordClient;
import static com.jenniferhawk.Bot.twitchClient;

public class JenniferGoLive {

    private final String SP0CK1_STREAM_LINK = "https://www.twitch.tv/sp0ck1";

    public JenniferGoLive(EventManager eventManager) {
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(ChannelGoLiveEvent.class, this::onGoLiveEvent);

    }

    public void onGoLiveEvent(ChannelGoLiveEvent event) {

        TextChannel streamIsHappening = discordClient.getTextChannelById("627611883335319602");


            if (event.getChannel().getName().toLowerCase().equals("sp0ck1")) {

                if (streamIsHappening != null) {
                    streamIsHappening.sendMessage("Sp0ck1 went live! " +
                            event.getTitle() + SP0CK1_STREAM_LINK).queue();
                }
            }


        JChatPane.appendText(event.getChannel().getName() + " went live!");

        Utils.updateSubscriberInfo();
    }
}
