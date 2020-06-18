package com.jenniferhawk.discord;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.common.events.channel.ChannelGoLiveEvent;
import net.dv8tion.jda.api.entities.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jenniferhawk.Bot.discordClient;


public class JenniferGoLive {

    private static final Logger LOG = LoggerFactory.getLogger(JenniferGoLive.class);

    private final String SP0CK1_STREAM_LINK = "https://www.twitch.tv/sp0ck1";

    public JenniferGoLive(EventManager eventManager) {
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(ChannelGoLiveEvent.class, this::onGoLiveEvent);

    }

    public void onGoLiveEvent(ChannelGoLiveEvent event) {

        LOG.debug("ChannelGoLiveEvent was fired and received by JenniferGoLive class");
        TextChannel streamIsHappening = discordClient.getTextChannelById("627611883335319602");
        if (streamIsHappening != null) {
            LOG.info("Sending message to stream-is-happening");
            if (event.getChannel().getName().toLowerCase().equals("sp0ck1")) {
                streamIsHappening.sendMessage("Sp0ck1 went live! " +
                        event.getTitle() +" "+ SP0CK1_STREAM_LINK).queue();
            }
              } else {
                LOG.error("ChannelGoLiveEvent was fired, but TextChannel was null!");
        }
    }
}

