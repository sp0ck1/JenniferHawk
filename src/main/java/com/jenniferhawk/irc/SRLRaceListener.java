package com.jenniferhawk.irc;

import com.jenniferhawk.Bot;
import com.jenniferhawk.database.JenDB;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericChannelEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import static com.jenniferhawk.irc.IRCBot.SRL;

// TODO:
//  - Contingency for .undone
//  - Do something with the resulting message

public class SRLRaceListener extends ListenerAdapter {

    String srlRoom;

    public SRLRaceListener(String srlRoom) {
        SRL.getConfiguration().getListenerManager().addListener(this);
        SRL.sendIRC().joinChannel(srlRoom);
        this.srlRoom = srlRoom;
    }

    public void leaveRoom() {
    }
    @Override
    public void onMessage(MessageEvent event) {
//        System.out.println("Message received from IRC: " + event.getMessage());
//        System.out.println("Channel source: " + event.getChannelSource());
//        System.out.println("Channel name: " + event.getChannel().getName());
//        System.out.println("Channel key: " + event.getChannel().getChannelKey() + "\nChannel Mode: " + event.getChannel().getMode() +
//                "\nChannel topic: " +
//                event.getChannel().getTopic());
//        System.out.println("V3 tags: " + event.getV3Tags());
//        System.out.println("Regular tags: " + event.getTags());
//        System.out.println("Nickname: " + event.getUser().getNick());
//        System.out.println("Real name: " + event.getUser().getRealName());
        String message = event.getMessage().toLowerCase();
        String user = event.getMessage().substring(event.getMessage().indexOf(" "));

        if (event.getChannelSource().equals(srlRoom)) {

//            if (message.contains(" 1st ") ||
//                    message.contains(" 2nd ") ||
//                    message.contains(" 3rd ") ||
//                    message.contains(" 4th ") ||
//                    message.contains(" 5th ")) {
            if (message.contains(" has finished in ")) {
                Bot.twitchClient.getChat().sendMessage("sp0ck1", event.getMessage());
                String[] array = message.split(" ");
                String runner = array[0];
                String place = array[4];

                // Correct for tapioca's username
                // will add other usernames here as necessary
                if (runner.equals("tapioca2000")) {
                    runner = "tapioca";
                }
                switch (place) {
                    case "1st":
                    case "first":
                        JenDB.N64UpdateCurrent(runner, "FIRST");
                        break;
                    case "2nd":
                    case "second":
                        JenDB.N64UpdateCurrent(runner, "SECOND");
                        break;
                    case "3rd":
                    case "third":
                        JenDB.N64UpdateCurrent(runner, "THIRD");
                        break;
                    case "4th":
                    case "fourth":
                        JenDB.N64UpdateCurrent(runner, "FOURTH");
                        break;
                    case "5th":
                    case "fifth":
                        JenDB.N64UpdateCurrent(runner, "FIFTH");
                        break;
                }
            }
        }
    }

    // What to do to remove this listener when the race room is closed?
    @Override
    public void onGenericChannel(GenericChannelEvent event) {

    }
}

