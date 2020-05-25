/*package com.jenniferhawk.tests;

import com.github.twitch4j.helix.domain.Subscription;
import com.github.twitch4j.helix.domain.SubscriptionList;
import com.jenniferhawk.Bot;
import org.junit.*;

import javax.security.auth.login.LoginException;

import static com.jenniferhawk.Bot.*;

public class GetSubscribersTest {

    @Test
    public void getChannelSubscribersByID() throws LoginException, InterruptedException {

        Bot bot = new Bot();
        bot.start();

        int subCount = -1;
        int channelPoints = -3;

        SubscriptionList subList = twitchClient.getHelix().getSubscriptions(OAUTH, BROADCASTER_ID, null, null, 1).execute();
        String cursor = subList.getPagination().getCursor();
        Subscription firstSub = subList.getSubscriptions().get(0); // Twitch counts the broadcaster as a sub, so this will always exist.
        String tier = firstSub.getTier();

        channelPoints = channelPoints + getTierValue(tier);
        subCount = subCount + subList.getSubscriptions().size();

        while (subList.getSubscriptions().size() != 0) {
            subList = twitchClient.getHelix().getSubscriptions(OAUTH,BROADCASTER_ID,cursor,null,100).execute();
            cursor = subList.getPagination().getCursor();

            for (Subscription sub : subList.getSubscriptions()) {
                String subscriberName = sub.getUserName();
                String subscriberTier = sub.getTier();
                channelPoints = channelPoints + getTierValue(subscriberTier);
                subCount++;
            }
        }
        System.out.println("I have " + subCount + " subs.");
        System.out.println("I have " + channelPoints + " channel points.");
    }

    public int getTierValue(String subscriberTier) {
        int value = 0;
        switch(subscriberTier) {
            case "1000": value = 1;
                break;
            case "2000": value = 2;
                break;
            case "3000": value = 3;
                break;
        }
        return value;
    }

}


/*
        for (Subscription sub : secondSubList) {


            System.out.println(subscriberName + " is subscribed at tier " + subscriberTier);


        }

       System.out.println(subCount);
*/