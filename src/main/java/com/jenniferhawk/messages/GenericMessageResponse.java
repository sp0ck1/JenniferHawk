package com.jenniferhawk.messages;

import com.github.twitch4j.common.enums.CommandPermission;

import com.jenniferhawk.database.JenDB;
import com.jenniferhawk.database.N64Game;
import net.dv8tion.jda.api.entities.MessageChannel;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Set;


import static com.jenniferhawk.Bot.*;
import static java.lang.Integer.parseInt;

public class GenericMessageResponse implements IncomingMessage, GenericCommandResponse {

    boolean isCommand;
    String message;
    String user;
    String sourceChannel;
    MessageType messageType;
    String command;
    String newCommandName;
    String newCommandResponse;
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, YYYY h:mm z");
    MessageChannel discordChannel;
    Set<CommandPermission> permissionType;
    String newTitle;

    @Override
    public GenericCommandResponse setNewCommandName(String newCommandName) {
        this.newCommandName = newCommandName;
        return this;
    }
    
    @Override
    public GenericCommandResponse setNewCommandResponse(String newCommandText) {
        this.newCommandResponse = newCommandText;
        return this;
    }
    
    @Override
    public GenericCommandResponse setMessageType(MessageType type) {
        this.messageType = type;
        return this;
    }

    @Override
    public MessageType getMessageType() {
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
        System.out.println("The source channel that was just set is " + sourceChannel);
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
                twitchClient.getChat().sendMessage(sourceChannel,message);
             //   JChatPane.appendText("JenniferHawk: " + message);
                break;
        }
    }


    @Override
    public void receiveMessage() {
        String message = "";

        if(isCommand) {
            switch (command) {
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
//                case "gameid":
//                    int ID = parseInt(newCommandName);
//                    String[] info = JenDB.n64Info(ID);
//                    message =
//                            info[0] +
//                                    " was released in " + info[1] +
//                                    ". "+ info[2] +
//                                    " developed it and " + info[3] +
//                                    " published it. It was released in " + info[4] +
//                                    " . It's in the " + info[5] + " genre.";
//                    break;
                default:
                    message = JenDB.queryHer(command);
            }
            if (!message.equals("")) {
                respond(message);
            }
        }
    }
}
