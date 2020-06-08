package com.jenniferhawk.messages;

import com.github.twitch4j.common.enums.CommandPermission;
import net.dv8tion.jda.api.entities.MessageChannel;
import java.util.Set;


interface GenericCommandResponse {

    GenericCommandResponse setNewCommandName(String newCommandName);

    GenericCommandResponse setNewCommandResponse(String newCommandText);

    GenericCommandResponse setMessageType(IncomingMessage.MessageType type);

    IncomingMessage.MessageType getMessageType();
    
    GenericCommandResponse setMessage(String message);

    GenericCommandResponse setUser(String user);

    GenericCommandResponse setPermissionType(Set<CommandPermission> permissionType);

    GenericCommandResponse setSourceChannel(String sourceChannel);

    GenericCommandResponse setNewTitle(String newTitle);

    GenericCommandResponse setIsCommand(boolean command);

    GenericCommandResponse setCommand(String commandPhrase);


    /**
     * Returns the name of the user who sent the message
     *
     * @return String
     */
    
    String getUser();

    /**
     * Returns the text of the message sent
     *
     * @return String
     */
    
    String getMessage();

    /**
     * Checks if the first character was a '!' char
     *
     * @return boolean
     */
    
    boolean isCommand();

    /**
     * Returns the source of the message, either a Twitch channel name or a Discord channel ID
     *
     * @return String
     */

    String getSourceChannel();

    
    
    GenericCommandResponse setDiscordChannel(MessageChannel discordChannel);


    void respond(String message);



    void receiveMessage();
}
