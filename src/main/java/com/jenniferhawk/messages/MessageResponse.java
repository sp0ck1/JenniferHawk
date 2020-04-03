package com.jenniferhawk.messages;

import net.dv8tion.jda.api.entities.TextChannel;

import static com.jenniferhawk.Bot.discordClient;
import static com.jenniferhawk.Bot.twitchClient;

public class MessageResponse implements IncomingMessage {

    String message;
    String user;
    String sourceChannel;
    boolean isCommand;
    MessageType messageType;

    MessageResponse() {

    }

    MessageResponse setMessageType(MessageType type) {
        this.messageType = type;
        return this;
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    public MessageResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public MessageResponse setUser(String user) {
        this.user = user;
        return this;
    }

    public MessageResponse setSourceChannel(String sourceChannel) {
        System.out.println("The source channel that was just set is " + sourceChannel);
        this.sourceChannel = sourceChannel;
        return this;
    }

    public MessageResponse setIsCommand(boolean command) {
        isCommand = command;
        return this;
    }

    @Override
    public IncomingMessage respond() {
    sendMessage("Hello out there!");
        return this;

    }



    void sendMessage(String message) {
        switch (messageType) {
        case DISCORD:
            TextChannel channel = discordClient.getTextChannelById(sourceChannel);
            if (channel != null) {
                //channel.sendMessage(message).queue();
            }
            break;
        case TWITCH:
            //twitchClient.getChat().sendMessage(sourceChannel,message);
            System.out.println("Channel message sending failed; channelId was null.");
        }

    }

    /**
     * Returns the name of the user who sent the message
     *
     * @return String
     */
    @Override
    public String getUser() {
        return null;
    }

    /**
     * Returns the text of the message sent
     *
     * @return String
     */
    @Override
    public String getMessage() {
        return null;
    }

    /**
     * Checks if the first character was a '!' char
     *
     * @return boolean
     */
    @Override
    public boolean isCommand() {
        return false;
    }

    /**
     * Returns the source of the message, either a Twitch channel name or a Discord channel ID
     *
     * @return String
     */
    @Override
    public String getSourceChannel() {
        return null;
    }
}
