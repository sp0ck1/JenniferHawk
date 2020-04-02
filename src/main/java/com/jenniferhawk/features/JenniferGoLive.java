package com.jenniferhawk.features;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.SubscriptionEvent;
import com.github.twitch4j.common.events.channel.ChannelGoLiveEvent;

import static com.jenniferhawk.Bot.twitchClient;

public class JenniferGoLive {

    public JenniferGoLive(EventManager eventManager) {
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(ChannelGoLiveEvent.class, this::onGoLiveEvent);

    }

    public void onGoLiveEvent(ChannelGoLiveEvent event) {
        System.out.println(event.getChannel().getName() + " went live!");

    }
}
