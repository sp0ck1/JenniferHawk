package com.JenniferHawk.features;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.JenniferHawk.JenniferGUI.JChatPane;

import javax.swing.*;

public class WriteChannelChatToConsole {

    JChatPane status;

    /**
     * Register events of this class with the EventManager
     *
     * @param eventManager EventManager
     */
    public WriteChannelChatToConsole(EventManager eventManager) {
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }

    /**
     * Subscribe to the ChannelMessage Event and write the output to the console
     */

        public void onChannelMessage(ChannelMessageEvent event) {

            status.appendText(event.getUser().getName() + " says: " + event.getMessage());

            System.out.printf(
                    "Channel: %s | %s: %s \n",
                    event.getChannel().getName(),
                    event.getUser().getName(),
                    event.getMessage()
            );
        }
    }
