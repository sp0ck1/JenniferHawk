package com.jenniferhawk.n64mania;

import com.github.twitch4j.common.enums.CommandPermission;
import com.jenniferhawk.database.JenDB;
import com.jenniferhawk.messages.GenericCommandResponse;
import com.jenniferhawk.messages.IncomingMessage;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Set;

import static com.jenniferhawk.Bot.*;
import static java.lang.Integer.parseInt;

public class N64ManiaMessageResponse implements GenericCommandResponse {

    Logger LOG = LoggerFactory.getLogger(N64ManiaMessageResponse.class);
    boolean isCommand;
    String message;
    String user;
    String sourceChannel;
    IncomingMessage.MessageType messageType;
    String command; // is always N64mania
    String n64Command; //
    String newCommandResponse;
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, YYYY h:mm z");
    MessageChannel discordChannel;
    Set<CommandPermission> permissionType;
    String newTitle;
    String argumentString;
    String[] argumentList;

    @Override
    public GenericCommandResponse setArgumentList(String[] argumentList) {
        this.argumentList = argumentList;
        return this;
    }

    @Override
    public GenericCommandResponse setNewCommandName(String newCommandName) {
        this.n64Command = newCommandName;
        return this;
    }

    @Override
    public GenericCommandResponse setNewCommandResponse(String newCommandText) {
        this.newCommandResponse = newCommandText;
        return this;
    }

    @Override
    public GenericCommandResponse setMessageType(IncomingMessage.MessageType type) {
        this.messageType = type;
        return this;
    }

    @Override
    public IncomingMessage.MessageType getMessageType() {
        return messageType;
    }

    @Override
    public GenericCommandResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public GenericCommandResponse setUser(String user) {
        this.user = user;
        return this;
    }

    @Override
    public GenericCommandResponse setPermissionType(Set<CommandPermission> permissionType) {
        this.permissionType = permissionType;
        return this;
    }

    @Override
    public GenericCommandResponse setSourceChannel(String sourceChannel) {

        this.sourceChannel = sourceChannel;
        return this;
    }

    @Override
    public GenericCommandResponse setNewTitle(String newTitle) {
        this.newTitle = newTitle;
        return this;
    }

    @Override
    public GenericCommandResponse setIsCommand(boolean command) {
        isCommand = command;
        return this;
    }

    @Override
    public GenericCommandResponse setCommand(String commandPhrase) {
        this.command = commandPhrase;
        return this;
    }


    /**
     * Returns the name of the user who sent the message
     *
     * @return String
     */

    @Override
    public String getUser() {
        return user;
    }

    /**
     * Returns the text of the message sent
     *
     * @return String
     */

    @Override
    public String getMessage() {
        return message;
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
        return sourceChannel;
    }

    @Override
    public GenericCommandResponse setDiscordChannel(MessageChannel discordChannel) {
        this.discordChannel = discordChannel;
        return this;
    }

    @Override
    public void respond(String message) {
        switch (messageType) {
            case DISCORD:
                discordChannel.sendMessage(message).queue();
                break;
            case TWITCH:
                twitchClient.getChat().sendMessage(sourceChannel, message);

                break;
        }
    }

    public void setArgumentString(String argumentString) {
        this.argumentString = argumentString;
    }

    @Override
    public void receiveMessage() {

        if (n64Command != null) {

            switch (n64Command) { // In this class, the n64Command is the word after !n64mania
                case "new":
                    Integer gID = parseInt(newCommandResponse);
                    String current = JenDB.setNewN64Game(gID);
                    message = "Current game has been set to: " + current;
                    break;
                case "lookup":
                    String lookup = newCommandResponse;
                    String[] maybeGame = JenDB.N64Lookup(lookup);
                    if (maybeGame[1] == null) {
                        message = "I'm not sure! Could you be a little more specific? Do you remember anything else?";

                    } else {
                        message = "Did you want " + maybeGame[1] + "? It's !GameID " + maybeGame[0] + ".";
                    }
                    break;
                case "complete":
                    JenDB.N64Complete();
                    break;
                case "clear":
                    JenDB.N64Clear();
                    break;
                case "first":
                case "second":
                case "third":
                case "fourth":
                case "fifth":
                    String runner = newCommandResponse;
                    String column = n64Command.toUpperCase(); // example !n64mania first tapioca
                    JenDB.N64UpdateCurrent(runner, column);
                    break;
                case "link":
                    String url = newCommandResponse;
                    JenDB.N64UpdateCurrent(url, "URL");
                    break;
                default: setMessageToDefault();
            }
        } else {
            setMessageToDefault();
        }

        if (!message.equals("")) {
            respond(message);
        }
    }

    private void setMessageToDefault() {
        LOG.debug("Sending n64Mania base command.");
        String[] currentGame = JenDB.N64Current();
        if (currentGame[0] == null) {
            message = "This week's game hasn't been decided yet! Use !rolln64 to give us some suggestions.";
        } else {
            message = "This week's N64Mania game is " + currentGame[1] + ". The race starts around 9PM EST on Friday! Use !GameID " + currentGame[0] + " for more info about the game.";
        }
    }
}
