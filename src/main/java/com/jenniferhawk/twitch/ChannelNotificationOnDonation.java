package com.jenniferhawk.twitch;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.DonationEvent;

public class ChannelNotificationOnDonation {

/*
     * Register events of this class with the EventManager
     *
     * @param eventManager EventManager
*/

    public ChannelNotificationOnDonation(EventManager eventManager) {
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(DonationEvent.class, event -> onDonation(event));
    }

/*
    * Subscribe to the Donation Event */


    public void onDonation(DonationEvent event) {
        String message = String.format(
                "%s just donated %s using %s!",
                event.getUser().getName(),
                event.getAmount(),
                event.getSource()
        );

        event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
    }

}
