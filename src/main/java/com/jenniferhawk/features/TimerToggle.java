package com.jenniferhawk.features;

import com.jenniferhawk.database.JenDB;



import java.text.SimpleDateFormat;
import java.util.concurrent.*;

import static com.jenniferhawk.Bot.twitchClient;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

public class TimerToggle {

    private static boolean value;
    private ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
   // private Disposable pokeFacts;
    SimpleDateFormat sdf = new SimpleDateFormat("h:mm a z 'on' MMMM d");

    public void TimerOn() {

        TimedCommands();
        value = false;
        System.out.println(value);

    }

    public void TimerOff() {

       value = true;
        System.out.println(value);
    }

    private void sendTimedMessage() {
      String message = JenDB.getTimedMessage();
      twitchClient.getChat().sendMessage("sp0ck1", message);
      //  JChatPane.appendText("JenniferHawk: " + message);

    }




    public static void sendPokeFact() {
        String fact = JenDB.getPokeFact();
        twitchClient.getChat().sendMessage("sp0ck1",fact);
      //  JChatPane.appendText("JenniferHawk: " + fact);
    }

//    public void pokeOn() {
//        Runnable pokeTimer = () -> sendPokeFact();
//        pokeFacts = twitchClient.getPubSub().getEventManager().getEventHandler(ReactorEventHandler.class).getScheduler().schedulePeriodically(pokeTimer, 0, 4, SECONDS);
//    }

//    public void pokeStop() {
//        pokeFacts.dispose();
//    }

    private ScheduledFuture TimedCommands() {
        System.out.println("Starting timed commands.");
        final Runnable Timer = () -> sendTimedMessage();
        ScheduledFuture<?> timerHandle =
                scheduler.scheduleAtFixedRate(Timer, 1, 5, MINUTES); // Fire every 5 minutes

        scheduler.setRemoveOnCancelPolicy(true);
        scheduler.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);

        ScheduledExecutorService  timerCancelCheckExecutor = Executors.newSingleThreadScheduledExecutor();
        timerCancelCheckExecutor.scheduleAtFixedRate(() -> {
            if( value ) timerHandle.cancel(true);  },1,1,SECONDS);

        return timerHandle;
    }

    public void pyramid(String channel) throws InterruptedException {
        twitchClient.getChat().sendMessage(channel," TehePelo ");
        Thread.sleep(1500); System.out.println(1);
        twitchClient.getChat().sendMessage(channel," TehePelo TehePelo ");
        Thread.sleep(1500); System.out.println(2);
        twitchClient.getChat().sendMessage(channel," TehePelo TehePelo  TehePelo  ");
        Thread.sleep(1500);System.out.println(3);
        twitchClient.getChat().sendMessage(channel," TehePelo  TehePelo ");
        Thread.sleep(1500);System.out.println(4);
        twitchClient.getChat().sendMessage(channel," TehePelo ");
    }

}

    //Every 10 minutes, send a random message from the timed_commands table WHERE TIMER = 1:
