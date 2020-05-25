package com.jenniferhawk.messages;

import com.github.twitch4j.common.enums.CommandPermission;

import com.jenniferhawk.database.JenDB;
import com.jenniferhawk.database.N64Game;
import net.dv8tion.jda.api.entities.MessageChannel;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Set;


import static com.jenniferhawk.Bot.*;

public class MessageResponse implements IncomingMessage {

    boolean isCommand;
    String message;
    String user;
    String sourceChannel;
    MessageType messageType;
    String commandPhrase;
    String newCommandName;
    String newCommandResponse;
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, YYYY h:mm z");
    MessageChannel discordChannel;
    Set<CommandPermission> permissionType;
    String newTitle;


    public MessageResponse setNewCommandName(String newCommandName) {
        this.newCommandName = newCommandName;
        return this;
    }

    public MessageResponse setNewCommandResponse(String newCommandText) {
        this.newCommandResponse = newCommandText;
        return this;
    }

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

    public MessageResponse setPermissionType(Set<CommandPermission> permissionType) {
        this.permissionType = permissionType;
        return this;
    }

    public MessageResponse setSourceChannel(String sourceChannel) {
        System.out.println("The source channel that was just set is " + sourceChannel);
        this.sourceChannel = sourceChannel;
        return this;
    }

    public MessageResponse setNewTitle(String newTitle) {
        this.newTitle = newTitle;
        return this;
    }

    public MessageResponse setIsCommand(boolean command) {
        isCommand = command;
        return this;
    }

    public MessageResponse setCommandPhrase(String commandPhrase) {
        this.commandPhrase = commandPhrase;
        return this;
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
        return sourceChannel;
    }

    public MessageResponse setDiscordChannel(MessageChannel discordChannel) {
        this.discordChannel = discordChannel;
        return this;
    }


    void respond(String message) {
        switch (messageType) {
            case DISCORD:
                    discordChannel.sendMessage(message).queue();
                break;
            case TWITCH:
                twitchClient.getChat().sendMessage(sourceChannel,message);
             //   JChatPane.appendText("JenniferHawk: " + message);
                break;
        }
    }


    @Override
    public void receiveMessage() {
        String message = "";

        if(isCommand) {
            switch (commandPhrase) {
                case "time":
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String time = sdf.format(timestamp);
                    message = "It's " + time + " for sp0ck1.";
                    break;
                case "roar":
                    message = user+" lets out a mighty ROAR!";
                    break;
                case "hydromedia":
                    message = "The Elder Scrolls III: Prince Street Pizza";
                    break;
                case "set":
                    JenDB.addToHer(newCommandName, newCommandResponse, user);
                    message = "I set the command...probably TehePelo";
                    break;
                case "title":
                    if (permissionType.contains(CommandPermission.MODERATOR)) {
                        twitchClient.getKraken().updateTitle(OAUTH, BROADCASTER_ID,newTitle).execute();
                        respond("Updated channel title to \"" + newTitle+"\"");
                    }
                    break;
                case "newcolor":

                                       break;
                case "poke":
                    message = JenDB.getPokeFact();
                    break;
                case "uptime":
                    message = "uptime";
                    break;
                case "clear":
                    System.out.println("User is: " + user);
                    if (user.equals("sp0ck1")) {
                        JenDB.deleteFromHer(newCommandName);
                        message = "I delete! I delete! But most importantly, I delete! ...I think";
                    }
                    break;
                case "pyramid":
                    if (sourceChannel.equals("sp0ck1") && permissionType.contains(CommandPermission.MODERATOR)) {
                        respond(" TehePelo ");
                        respond(" TehePelo TehePelo ");
                        respond(" TehePelo TehePelo  TehePelo  ");
                        respond(" TehePelo TehePelo ");
                        respond(" TehePelo ");
                    }
                    break;
                case "rolln64":
                    N64Game n64Game = JenDB.rolln64();
                    if (messageType == MessageType.DISCORD) {
                    message = "";
                    } else {
                        message = user + ", you are responsible for suggesting " +
                                n64Game.getTitle() +
                                ". For more info, use !gameid " +
                                n64Game.getId();
                    }
                    break;
                default:
                    message = JenDB.queryHer(commandPhrase);
            }
            if (!message.equals("")) {
                respond(message);
            }
        }
    }
}
