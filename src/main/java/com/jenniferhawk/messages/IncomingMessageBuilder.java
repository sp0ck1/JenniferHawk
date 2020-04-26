package com.jenniferhawk.messages;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Set;

public class IncomingMessageBuilder extends ListenerAdapter {

    String newCommandName;
    String newCommandResponse;
    String commandPhrase;
    boolean isCommand;
    String newTitle;



    /**
     * Empty constructor used to instantiate class for <code>Bot#registerFeatures</code>
     */
    public IncomingMessageBuilder() {

    }

    /**
     * Intercepts Twitch messages and sends them to be built by the <code>buildTwitchMessage</code> method.
     * @param eventManager
     */
    public IncomingMessageBuilder(EventManager eventManager) {
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(ChannelMessageEvent.class, this::buildTwitchMessage);
    }



    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (!event.getAuthor().getName().toLowerCase().equals("jenniferhawk")) {
            buildDiscordMessage(event);
        }
    }

    public void buildTwitchMessage(ChannelMessageEvent event) {
        if (event == null) {
            return;
        }

        String message = event.getMessage();
        parseMessage(message);
        String user = event.getUser().getName().toLowerCase();
        String sourceChannel = event.getChannel().getName();
        Set<CommandPermission> permissionType = event.getPermissions();

        IncomingMessage.MessageType messageType = IncomingMessage.MessageType.TWITCH;

        System.out.println("The source Twitch channel in IMB is: " + event.getChannel().getName());
        new MessageResponse()
                .setMessage(message)
                .setUser(user)
                .setSourceChannel(sourceChannel)
                .setIsCommand(isCommand)
                .setMessageType(messageType)
                .setCommandPhrase(commandPhrase)
                .setNewCommandName(newCommandName)
                .setNewCommandResponse(newCommandResponse)
                .setPermissionType(permissionType)
                .setNewTitle(newTitle)
                .receiveMessage();
    }

    public void buildDiscordMessage(MessageReceivedEvent event){
        if (event == null) {
            return;
        }
        String message = event.getMessage().getContentDisplay();
        parseMessage(message);

        String user = event.getAuthor().getName().toLowerCase();
        String sourceChannel = event.getChannel().getName();
        MessageChannel discordChannel = event.getChannel();
        IncomingMessage.MessageType messageType = IncomingMessage.MessageType.DISCORD;

        System.out.println("The source Discord channel in IMB is: " + event.getChannel().getName());
        new MessageResponse()
                .setMessage(message)
                .setUser(user)
                .setSourceChannel(sourceChannel)
                .setIsCommand(isCommand)
                .setMessageType(messageType)
                .setCommandPhrase(commandPhrase)
                .setNewCommandName(newCommandName)
                .setNewCommandResponse(newCommandResponse)
                .setDiscordChannel(discordChannel)
                .receiveMessage();
    }

    public void parseMessage(String message){
        int messageLength =  message.split(" ",3).length;
        isCommand = message.startsWith("!");
        String[] splitMessage = message.split(" ",3);
        commandPhrase = isCommand ? splitMessage[0].replaceAll("!","").toLowerCase() : null;
        newCommandName = messageLength >= 2 ? splitMessage[1].toLowerCase() : null;  // Will use if commandPhrase.equals("set")
        newCommandResponse = messageLength >= 3 ? splitMessage[2] : null; // ^^
        newTitle = messageLength >= 2 ? message.split(" ",2)[1] : null;
    }

    public enum TwitchPermissions {
        BROADCASTER, MODERATOR, SUBSCRIBER, VIP, NONE
    }
}
