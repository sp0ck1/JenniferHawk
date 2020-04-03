package com.jenniferhawk.messages;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class IncomingMessageBuilder extends ListenerAdapter {

    public IncomingMessageBuilder(EventManager eventManager) {
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(ChannelMessageEvent.class, this::buildTwitchMessage);
    }

    public IncomingMessageBuilder() {

    }


    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (!event.getAuthor().getName().toLowerCase().equals("jenniferhawk")) {
            buildDiscordMessage(event);
            System.out.println("Do you hear me?");
        }
    }

    public IncomingMessage buildTwitchMessage(ChannelMessageEvent event) {
        if (event == null) {
            return null;
        }
        System.out.println("The source channel in IMB is: " + event.getChannel().getName());
        return new MessageResponse()
                .setMessage(event.getMessage())
                .setUser(event.getUser().getName())
                .setSourceChannel(event.getChannel().getName())
                .setIsCommand(event.getMessage().startsWith("!"))
                .setMessageType(IncomingMessage.MessageType.TWITCH)
                .respond();
    }

    public IncomingMessage buildDiscordMessage(MessageReceivedEvent event){
        if (event == null) {
            return null;
        }
        System.out.println("The source channel in IMB is: " + event.getChannel().getName());
        return new MessageResponse()
                .setMessage(event.getMessage().getContentDisplay())
                .setSourceChannel(event.getChannel().getId())
                .setUser(event.getAuthor().getName())
                .setIsCommand(event.getMessage().getContentDisplay().startsWith("!"))
                .setMessageType(IncomingMessage.MessageType.DISCORD)
                .respond();
    }


}
