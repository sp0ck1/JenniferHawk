package com.jenniferhawk.twitch;

import com.github.philippheuer.events4j.core.domain.Event;
import com.github.twitch4j.common.events.channel.ChannelGoLiveEvent;
import com.github.twitch4j.common.events.channel.ChannelGoOfflineEvent;
import com.github.twitch4j.common.events.domain.EventChannel;
import com.github.twitch4j.helix.domain.Stream;
import com.github.twitch4j.helix.domain.StreamList;
import com.jenniferhawk.Bot;
import com.jenniferhawk.utils.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class ChannelGoLiveCheck {

    // Fires a ChannelGoLiveEvent if a channel in the channelsToCheck is live.
    // Fires a ChannelGoOfflineEvent if a channel in the liveChannels is not in the resultList

    Logger LOG = LoggerFactory.getLogger(ChannelGoLiveCheck.class);
    public static boolean isLive;
    List<String> channelsToCheck = new ArrayList<>();
    Scheduler goLiveScheduler = new Scheduler();
    Set<String> liveChannels = new HashSet<>();

    public ChannelGoLiveCheck() {
        Runnable goLiveRunnable = () -> {
            goLiveCheck();
        };
        // Check every minute if streams in the list are online/offline
        goLiveScheduler.schedule(goLiveRunnable,1,5, TimeUnit.SECONDS);
    }

    public ChannelGoLiveCheck addChannel(String channel) {
        channelsToCheck.add(channel.toLowerCase());
        return this;
    }

    public ChannelGoLiveCheck removeChannel(String channel) {
        channelsToCheck.remove(channel.toLowerCase());
        return this;
    }

    private ChannelGoLiveCheck removeLiveChannel(String channel){
        liveChannels.remove(channel);
        return this;
    }

    private ChannelGoLiveCheck addLiveChannel(String channel){
        liveChannels.add(channel);
        return this;
    }



    private void goLiveCheck() {
            //System.out.println("Hi my name is goLiveCheck! I'm broken :(...");
        ArrayList<String> streamUsernameList  = new ArrayList<>();

        if (channelsToCheck.size() != 0) { // Cannot pass empty string as lookup value
            StreamList resultList = Bot.twitchClient.getHelix().getStreams(
                    Bot.OAUTH,
                    "",
                    "",
                    channelsToCheck.size(),
                    null,
                    null,
                    null,
                    null,
                    channelsToCheck).execute();
            resultList.getStreams().forEach(member -> streamUsernameList.add(member.getUserName())); // Add all the usernames to the list of returned usernames

            if (resultList.getStreams().size() != 0) { // If at least one stream in here is live

                for (Stream stream : resultList.getStreams()) {

                    LOG.info(stream.getUserName() + " is live!");

                    isLive = stream.getType().equals("live");

                    LOG.debug("isLive boolean is now " + isLive);
                    // If they're live and not already in the set of live channels...
                    if ( (isLive) && !(liveChannels.contains(stream.getUserName())) ) {
                        LOG.info(stream.getUserName() + " is live! Dispatching new ChannelGoLiveEvent for " + stream.getUserName());

                        Event goLiveEvent = new ChannelGoLiveEvent(
                                new EventChannel(stream.getId(), stream.getUserName()),
                                stream.getTitle(),
                                stream.getGameId());
                        Bot.twitchClient.getEventManager().publish(goLiveEvent);

                        addLiveChannel(stream.getUserName());
                      //  Bot.twitchClient.getChat().sendMessage(stream.getUserName(), stream.getUserName() + " is online!");
                    }
                   //  streamUsernameList.add(stream.getUserName()); // Add this name to the list of usernames returned in the resultList
                    }
            } else {
                // None of the streams were live
                isLive = false;
            }

            // If a username is in the liveChannels set but was not part of the resultList, remove them from the liveChannels and throw a ChannelGoOfflineEvent

            for (String username : liveChannels) {
                if (!streamUsernameList.contains(username)) {
                    removeLiveChannel(username);

                    Event goOfflineEvent = new ChannelGoOfflineEvent(
                            new EventChannel(null, username));
                    Bot.twitchClient.getEventManager().publish(goOfflineEvent);

                    removeLiveChannel(username);
                    Bot.twitchClient.getChat().sendMessage(username,"I hope you had a good stream!");
                }
            }
        }
    }



}
