package com.jenniferhawk.features;

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
import java.util.stream.Collectors;

public class ChannelGoLiveCheck {

    // Fires a ChannelGoLiveEvent if a channel in the channelsToCheck is live.
    // Fires a ChannelGoOfflineEvent if a channel in the liveChannels is not in the resultList

    Logger LOG = LoggerFactory.getLogger(ChannelGoLiveCheck.class);

    List<String> channelsToCheck = new ArrayList<>();
    Scheduler goLiveScheduler = new Scheduler();
    Set<String> liveChannels = new HashSet<>();

    public ChannelGoLiveCheck() {
        Runnable goLiveRunnable = () -> {
            goLiveCheck();
        };
        // Check every minute if streams in the list are online/offline
        goLiveScheduler.schedule(goLiveRunnable,1,1, TimeUnit.MINUTES);
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
        boolean isLive;
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

            if (resultList.getStreams().size() != 0) { // If at least one stream in here is live

                for (Stream stream : resultList.getStreams()) {

                    isLive = stream.getType().equals("live");

                    if ( (isLive) && !(liveChannels.contains(stream.getUserName())) ) { // If they're live and not already in the set of live channels...
                        LOG.info(stream.getUserName() + " is live! Dispatching new ChannelGoLiveEvent for " + stream.getUserName());

                        Event goLiveEvent = new ChannelGoLiveEvent(
                                new EventChannel(stream.getId(), stream.getUserName()),
                                stream.getTitle(),
                                stream.getGameId());
                        Bot.twitchClient.getEventManager().publish(goLiveEvent);

                        addLiveChannel(stream.getUserName());
                      //  Bot.twitchClient.getChat().sendMessage(stream.getUserName(), stream.getUserName() + " is online!");
                    }
                     streamUsernameList.add(stream.getUserName()); // Add this name to the list of usernames returned in the resultList
                    }
            } else {
                // None of the streams were live
            }

            // If a username is in the liveChannels set but was not part of the resultList, remove them from the liveChannels and throw a ChannelGoOfflineEvent
            resultList.getStreams().forEach(member -> streamUsernameList.add(member.getUserName())); // Add all the usernames to the list of returned usernames
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
