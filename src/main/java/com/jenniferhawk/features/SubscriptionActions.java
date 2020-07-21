
package com.jenniferhawk.features;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.SubscriptionEvent;


import java.sql.Timestamp;
import java.util.Random;

public class SubscriptionActions {

/**
     * Register events of this class with the EventManager
     *
     * @param eventManager EventManager
     */

    public SubscriptionActions(EventManager eventManager) {
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
                    "%s just subscribed! " + jenQuip(),
                    event.getUser().getName(),
                    event.getChannel().getName()
            );
        }

        // Resubscription
        if (event.getMonths() > 1 && isSp0ck1) {
            message = String.format(
                    "%s has re-subscribed! Wow! " + jenQuip(),
                    event.getUser().getName(),
                    event.getChannel().getName(),
                    event.getMonths()
            );
        }

        // Send Message
        event.getTwitchChat().sendMessage(event.getChannel().getName(), message);
      //  Utils.updateSubscriberInfo();
    }

    public String jenQuip() {
        Random random = new Random();
        int thisRand = random.nextInt(10);
        switch (thisRand) {
            case 0:
                return "Unbelievable!";
            case 1:
                return "This is incredible!";
            case 2:
                return "Thank you so much!";
            case 3:
                return "That's wonderful!";
            case 4:
                return "This is the best thing that's happened all day!";
            case 5:
                return "I never thought this would happen in 1 million years!";
            case 6:
                return "This is even better than barbecued pork!";
            case 7:
                return "10 loaves of my finest baguettes will be delivered to your doorstep tomorrow morning.";
            case 8:
                return "Please have this https://www.youtube.com/watch?v=OXqAbNqhZuk";
            case 9:
                return "30 years ago it was prophesied by the great prophet sg4e that this day would come, and now the prophecy has finally been realized.";
            default:
                return "Truly magnificent. Just beautiful.";

        }
    }
}

