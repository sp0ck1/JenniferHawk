package com.JenniferHawk.features;

import com.github.philippheuer.events4j.EventManager;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import java.math.BigInteger;

import static com.JenniferHawk.Bot.discordClient;

public class WriteChannelChatToConsole extends ListenerAdapter {

    /**
     * Register events of this class with the EventManager
     *
     * @param eventManager EventManager
     */
    public WriteChannelChatToConsole(EventManager eventManager) {
        eventManager.onEvent(ChannelMessageEvent.class).subscribe(event -> onChannelMessage(event));
    }

    public WriteChannelChatToConsole(MessageReceivedEvent event) {
        System.out.println(event.getMessage().getContentDisplay());
    }
    /**
     * Subscribe to the ChannelMessage Event and write the output to the console
     */
    public void onChannelMessage(ChannelMessageEvent event) {
        System.out.printf(
                "Channel: %s | %s: %s \n",
                event.getChannel().getName(),
                event.getUser().getName(),
                event.getMessage()
        );
    }
}
