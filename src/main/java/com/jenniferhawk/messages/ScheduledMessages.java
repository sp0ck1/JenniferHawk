package com.jenniferhawk.messages;

import com.jenniferhawk.Bot;
import com.jenniferhawk.database.JenDB;
import com.jenniferhawk.features.ChannelGoLiveCheck;
import com.jenniferhawk.utils.Scheduler;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;


public class ScheduledMessages {

    Scheduler messageScheduler = new Scheduler();
    Scheduler isLiveScheduler = new Scheduler();

    public ScheduledMessages() {
        Runnable sendScheduledMessage = this::sendScheduledMessage;

       // Runnable printIsLive = this::printLiveStatus;

        messageScheduler.schedule(sendScheduledMessage,2,20,MINUTES);
       // isLiveScheduler.schedule(printIsLive,1,3,SECONDS);
    }

    private void sendScheduledMessage() {
        // System.out.println("I ran this sendScheduledMessage function");
        if(ChannelGoLiveCheck.isLive) {
            // System.out.println("Channel is live");
            Bot.twitchClient.getChat().sendMessage("sp0ck1",JenDB.getTimedMessage());
        }
    }

   // private void printLiveStatus() {
    //    System.out.println(ChannelGoLiveCheck.isLive);
   // }
}
