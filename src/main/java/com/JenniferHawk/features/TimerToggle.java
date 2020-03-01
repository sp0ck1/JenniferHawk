package com.JenniferHawk.features;

import com.github.twitch4j.helix.domain.*;


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

import static com.JenniferHawk.Bot.twitchClient;
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
    }

    public void send24() {
        List<String> sg4e = Collections.singletonList("sg4e");
        StreamList resultList = twitchClient.getHelix().getStreams("oauth:124tkcsi4od4062r3i9q63pjs5qwr5",null,  null, 1, null, null, null, null, Collections.singletonList("sg4e")).execute();
        resultList.getStreams().forEach(stream -> {
            GameList gameList = twitchClient.getHelix().getGames("oauth:124tkcsi4od4062r3i9q63pjs5qwr5", Collections.singletonList(stream.getGameId()),null).execute();
            Game game = gameList.getGames().get(0);
            String gameInfo = String.valueOf(game);
            String gameName = game.getName();// gameInfo.substring(gameInfo.indexOf("name=")+5,gameInfo.indexOf(", b"));
            Calendar j = stream.getStartedAt();

            String startTime = sdf.format(j.getTime());

            twitchClient.getChat().sendMessage("sg4e",
            "Starting at "+startTime+", sg4e will be doing a 24 hour stream marathon. We'll start with everyone's favorite: "+gameName+"! " +
                    "Then we'll move on to YOUR Mario Maker 2 levels! Type !mm submit <level code> <comment> to add a level to the queue, " +
                    "use !queue to see what's next, and use !bully to get the latest information on whose records are being bopped. See you there! NoaGamba");
    });
    }


    public void sendPokeFact() {
        String fact = JenDB.getPokeFact();
        twitchClient.getChat().sendMessage("sp0ck1",fact);
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









