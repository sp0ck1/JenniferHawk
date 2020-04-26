package com.jenniferhawk.messages;

import com.github.twitch4j.chat.events.AbstractChannelEvent;
import net.dv8tion.jda.internal.entities.AbstractMessage;

public interface IncomingMessage {

    /**
     * Sends a message back to the appropriate source
     */
    void receiveMessage();

    /**
     * Returns the name of the user who sent the message
     * @return String
     */
    String getUser();

    /**
     * Returns the text of the message sent
     * @return String
     */
    String getMessage();

    /**
     * Checks if the first character was a '!' char
     * @return boolean
     */
    boolean isCommand();

    /**
     * Returns the source of the message, either a Twitch channel name or a Discord channel ID
     * @return String
     */
    String getSourceChannel();

    MessageType getMessageType();

    enum MessageType {
        TWITCH, DISCORD
    }
}

