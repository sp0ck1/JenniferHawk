package com.jenniferhawk.messages;

import com.jenniferhawk.Bot;
import com.jenniferhawk.database.JenDB;
import com.jenniferhawk.features.ChannelGoLiveCheck;
import com.jenniferhawk.utils.Scheduler;

import static java.util.concurrent.TimeUnit.MINUTES;

public class ScheduledMessages {

    Scheduler messageScheduler = new Scheduler();

    public ScheduledMessages() {
        Runnable sendScheduledMessage = () -> {
            sendScheduledMessage();
        };


        messageScheduler.schedule(sendScheduledMessage,1,10,MINUTES);
    }

    private void sendScheduledMessage() {
        if(ChannelGoLiveCheck.isLive) {
            Bot.twitchClient.getChat().sendMessage("sp0ck1",JenDB.getTimedMessage());
        }
    }
}
