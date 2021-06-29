package com.jenniferhawk.messages;

import com.github.twitch4j.chat.events.AbstractChannelEvent;
import net.dv8tion.jda.internal.entities.AbstractMessage;

import java.io.IOException;

public interface IncomingMessage {

    /**
     * Interprets a message in order to determine the appropriate actions to be taken in response
     */
    void receiveMessage() throws IOException;

    /**
     * Returns the name of the user who sent the message
     * @return String
     */
    String getUser();
;
    /**
     * Returns the text of the message sent
     * @return String
     */
    String getMessage();

    /**
     * Checks if the first character was a '!'
     * @return boolean
     */
    boolean isCommand();

    /**
     * Returns the source of the message, either a Twitch channel name or a Discord channel ID
     * @return String
     */
    String getSourceChannel();

    MessageType getMessageType();

    enum MessageType
    {
        TWITCH("<@!?(\\d+)>"),
        DISCORD("<@!?(\\d+)>");

        MessageType(String regex) {

        }

    }
}

