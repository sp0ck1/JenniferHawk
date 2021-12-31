package com.jenniferhawk.messages;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import com.jenniferhawk.N64Mania.N64ManiaMessageResponse;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class IncomingMessageBuilder extends ListenerAdapter {

    Logger LOG = LoggerFactory.getLogger(IncomingMessageBuilder.class);
    String newCommandName;
    String newCommandResponse;
    String commandPhrase;
    boolean isCommand;
    String newTitle;
    String user;
    String sourceChannel;
    MessageChannel discordChannel;
    IncomingMessage.MessageType messageType;
    String message;
    int messageLength;
    String[] splitMessage;
    Set<CommandPermission> permissionType;
    GenericCommandResponse response;
    String[] argumentList;
    /**
     * Empty constructor used to instantiate class for <code>Bot#discordClient</code>
     */
    public IncomingMessageBuilder() {
        
    }

    /**
     * Intercepts Twitch messages and sends them to be built by the <code>buildTwitchMessage</code> method.
     * @param eventManager
     */
    public IncomingMessageBuilder(EventManager eventManager) {
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(ChannelMessageEvent.class, this::parseMessageEvent);
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (!event.getAuthor().getName().toLowerCase().equals("jenniferhawk")) { // If Jennifer didn't send the message
            try {
                parseMessageEvent(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<Emote> emotes = event.getMessage().getEmotes();
        for (Emote emote : emotes) {
            System.out.println("Emote ID: " + emote.getId());
        }
    }

    public void buildMessageResponse() {

        if (isCommand() && commandPhrase.equals("n64mania")) {
            LOG.info("Building n64 response to message from source: " + sourceChannel);
            response = new N64ManiaMessageResponse()
                    .setMessage(message)
                    .setUser(user)
                    .setSourceChannel(sourceChannel)
                    .setIsCommand(isCommand())
                    .setMessageType(messageType)
                    .setCommand(commandPhrase)
                    .setNewCommandName(newCommandName)
                    .setNewCommandResponse(newCommandResponse)
                    .setPermissionType(permissionType)
                    .setNewTitle(newTitle)
                    .setMessage("");
        }
        else {


            LOG.info("Building generic response to message from source: " + sourceChannel);
            response = new GenericMessageResponse()
                    .setMessage(message)
                    .setUser(user)
                    .setSourceChannel(sourceChannel)
                    .setIsCommand(isCommand())
                    .setMessageType(messageType)
                    .setCommand(commandPhrase) // The first word
                    .setNewCommandName(newCommandName) // The second word
                    .setNewCommandResponse(newCommandResponse) // The third word/phrase
                    .setPermissionType(permissionType)
                    .setNewTitle(newTitle);

        }
        if (messageType == IncomingMessage.MessageType.DISCORD) {
            response.setDiscordChannel(discordChannel);
        }
        if (argumentList != null) {
            response.setArgumentList(argumentList);
        }
        response.receiveMessage();
    }


    public void parseMessageEvent(MessageReceivedEvent event) throws IOException {
        message = event.getMessage().getContentDisplay();
        splitMessage = message.split(" ",3);
        commandPhrase = isCommand() ? splitMessage[0].replaceAll("!","").toLowerCase() : null;
        newCommandName = getMessageLength() >= 2 ? splitMessage[1].toLowerCase() : null;  // Will use if commandPhrase.equals("set")
        newCommandResponse = getMessageLength() >= 3 ? splitMessage[2] : null; // ^^
        newTitle = getMessageLength() >= 2 ? message.split(" ",2)[1] : null;
        user = event.getAuthor().getName().toLowerCase();
        sourceChannel = event.getChannel().getName();
        discordChannel = event.getChannel();
        messageType = IncomingMessage.MessageType.DISCORD;

        if (message.contains("--")) {
            argumentList = message.split(" ");
        } else argumentList = null;

        buildMessageResponse();

        // Currently unused block to obtain a Discord user's roles. Probably doesn't fit in this class.
        // ---------
        // User author = event.getAuthor();
        // Guild guild = event.getGuild();
        // Member member = guild.getMember(author);
        // List<Role> roles = member.getRoles();
        // ---------

        //event.getMessage().getEmotes().forEach(emote -> System.out.println(emote.toString()));
    }

    public void parseMessageEvent(ChannelMessageEvent event) {
        message = event.getMessage();
        splitMessage = message.split(" ", 3);
        commandPhrase = isCommand() ? splitMessage[0].replaceAll("!", "").toLowerCase() : null;
        newCommandName = getMessageLength() >= 2 ? splitMessage[1].toLowerCase() : null;  // Will use if commandPhrase.equals("set")
        newCommandResponse = getMessageLength() >= 3 ? splitMessage[2] : null; // ^^
        newTitle = getMessageLength() >= 2 ? message.split(" ", 2)[1] : null;
        user = event.getUser().getName().toLowerCase();
        sourceChannel = event.getChannel().getName();
        permissionType = event.getPermissions();
        messageType = IncomingMessage.MessageType.TWITCH;
        event.getTwitchChat().ban("","","");

        if (message.contains("--")) {
            argumentList = message.split(" "); // If this works, change MessageReceivedEvent as well

        } else argumentList = null;
        buildMessageResponse();
    }

    private boolean isCommand() {
        return message.startsWith("!");
    }

    private int getMessageLength() {
        return message.split(" ", 3).length;
    }


    public enum PermissionType {
        BROADCASTER, MODERATOR, SUBSCRIBER, VIP, NONE
    }
}
