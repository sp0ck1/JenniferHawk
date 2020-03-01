
package com.JenniferHawk.features;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.SubscriptionEvent;

public class ChannelNotificationOnSubscription {



/**
     * Register events of this class with the EventManager
     *
     * @param eventManager EventManager
     */

    public ChannelNotificationOnSubscription(EventManager eventManager) {
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(SubscriptionEvent.class, this::onSubscription);
    }


/**
     * Subscribe to the Subscription Event
     *
*/
    public void onSubscription(SubscriptionEvent event) {
        String message = "";
        boolean isSp0ck1 = event.getChannel().getName().equalsIgnoreCase("Sp0ck1");

        // New Subscription
        if (event.getMonths() <= 1 && isSp0ck1) {
            message = String.format(
                    "%s has subscribed to %s!",
                    event.getUser().getName(),
                    event.getChannel().getName()
            );
        }

        // Resubscription
        if (event.getMonths() > 1 && isSp0ck1) {
            message = String.format(
                    "%s has subscribed! Wow!",
                    event.getUser().getName(),
                    event.getChannel().getName(),
                    event.getMonths()
            );
        }

        // Send Message
        event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
    }
}

