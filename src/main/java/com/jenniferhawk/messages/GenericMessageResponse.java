package com.jenniferhawk.messages;

import com.github.twitch4j.common.enums.CommandPermission;

import com.jenniferhawk.database.JenDB;
import com.jenniferhawk.database.N64Game;
import com.jenniferhawk.twitch.ChannelGoLiveCheck;
import com.jenniferhawk.howlongtobeat.HLTBEntry;
import com.jenniferhawk.irc.SRLRaceListener;
import com.jenniferhawk.utils.HLTBLookup;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


import static com.jenniferhawk.Bot.*;
import static com.jenniferhawk.Launcher.truckMoney;
import static java.lang.Integer.parseInt;

public class GenericMessageResponse implements IncomingMessage, GenericCommandResponse {

    boolean isCommand;
    String message;
    String user;
    String sourceChannel;
    MessageType messageType;
    String command;
    String secondWord;
    String phrase;
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, YYYY h:mm z");
    MessageChannel discordChannel;
    Set<CommandPermission> permissionType;
    String newTitle;


    @Override
    public GenericCommandResponse setNewCommandName(String newCommandName) {
        this.secondWord = newCommandName;
        return this;
    }
    
    @Override
    public GenericCommandResponse setNewCommandResponse(String newCommandText) {
        this.phrase = newCommandText;
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
                    JenDB.addToHer(secondWord, phrase, user);
                    message = "I set the command...probably TehePelo";
                    break;
                case "addunit":
                    if (permissionType.contains(CommandPermission.MODERATOR)) {
                        JenDB.addUEBSUnit(secondWord);
                        message = "I added the unit to the unit list, probably!";
                    }
                    break;
                case "getbattle":
                    System.out.println("heard getBattle command");
                    String team1 = JenDB.getBattleConfig();
                    String team2 = JenDB.getBattleConfig();
                    message = "How about " + team1 + " fights " + team2 + "?";
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
                    if (permissionType.contains(CommandPermission.MODERATOR)) {
                        JenDB.deleteFromHer(secondWord);
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
                case "addmoney":
                    if (sourceChannel.equals("sp0ck1") && permissionType.contains(CommandPermission.MODERATOR)) {
                        int newMoney;
                        try {
                            newMoney = Integer.parseInt(secondWord);
                            truckMoney = truckMoney + newMoney;
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    message = "New total: $ " + truckMoney;
                    break;
                case "clearmoney":
                    if (sourceChannel.equals("sp0ck1") && permissionType.contains(CommandPermission.MODERATOR)) {
                    truckMoney = 0; }
                    message = "!truckmoney has been reset to $0";
                    break;
                case "truckmoney":
                    message = "Sp0ck1 has made $" + truckMoney + " trucking today.";
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
                case "runback":
                    //TODO: assign positive and negative adjectives, so that the patterns
                    // "[negative] one, but" and "[positive] one, and" can occur

                    //TODO: Maybe if discrepency between the two numbers is large enough,
                    // always generate a negative response, or maybe have neutral responses also
                    String[] adjectives =
                            {"long","wild","frosty","ridiculous","spicy","juicy",
                            "kind of fun","stupid","short","hot","clammy","soft","hard",
                            "chaotic","slow","fast","cursed","foggy","stinky","slippery",
                            "bouncy","groan-inducing","hellish","silly","sticky",};
                    String[] finishers =
                            {"in my opinion","I'd say","no doubt about it","in my experience",
                            "that's what I was told","but Hydromedia might like it","but it was no San Francisco Rush: Extreme Racing (unless it was)",
                            "better than Kingdom Hearts at least","maybe good for a rainy day",
                            "and probably better than SM64 Chaos Edition", "and otherwise we might end up playing " +
                            JenDB.rolln64().getTitle()};
                    Random random = new Random();
                    int rating = random.nextInt(65);
                    int outOf = rating + random.nextInt(65-rating);
                    N64Game runbackGame = JenDB.rollRunback();
                    if (messageType == MessageType.DISCORD) {
                        user = user+"_7k";
                    }
                    message = user + ", would you suggest doing a runback of " +
                            runbackGame.getTitle() + "? " +
                            runbackGame.getWinner() + " won this race originally. " +
                            "Personally, I'd give it a " + rating +
                            " out of " + outOf + ". It was a " + adjectives[random.nextInt(adjectives.length)] +
                            " one, " + finishers[random.nextInt(finishers.length)] + ".";
                    break;
                case "gameid":
                    int ID = parseInt(secondWord);
                    N64Game game = JenDB.getGameInfo(ID);
                    message =
                            game.getTitle() +
                                    " is an N64 game in the " + game.getGenre() + " genre." +
                                    " It was developed by "+ game.getDeveloper() +
                                    " and published by " + game.getPublisher() +
                                    " in " + game.getYear() + ", " +
                                    " where it was released in " + game.getRegion() +
                                    ".";
                    break;
                case "joinsrl":
                    new SRLRaceListener(secondWord);
                    break;
                case "timed":
                    message = JenDB.getTimedMessage();
                    break;
                //case "livenow":
                  //  ChannelGoLiveCheck.isLive = !ChannelGoLiveCheck.isLive;
                    //System.out.println("Changed channelgolive and it's now this: " + ChannelGoLiveCheck.isLive);
                    //break;
                case "amilive":
                    message = "Is Sp0ck1 live? " + ChannelGoLiveCheck.isLive;
                    break;
                case "leaveapun":
                    JenDB.addPun(secondWord, phrase, user);
                    break;
                case "takeapun":
                    message = JenDB.getPun();
                    break;
                case "hltb":
                    HLTBEntry entry;
                    System.out.println("First word: " + command + " Second word: " + secondWord + " The rest of the phrase: " + phrase);
                    if (phrase != null) {
                        entry = HLTBLookup.searchGame(secondWord + " " + phrase);
                    } else {
                        entry = HLTBLookup.searchGame(secondWord);
                    }
                    if (entry != null) {
                        if (entry.getMainStoryTime() != null && !entry.getMainStoryTime().equals("0")) {
                        message = String.format("%s takes %s to beat, according to HLTB. Does that answer your question?",entry.getName(),entry.getMainStoryTime());
                        } else {
                            message = String.format("This game is on HowLongToBeat, but it doesn't have a time. %s", entry.getDetailLink());
                        }
                    } else {
                        message = "Couldn't find this game on HowLongToBeat! Try searching yourself at https://howlongtobeat.com/#search";
                    }
                    break;
                default:
                    message = JenDB.queryHer(command);
            }
            if (!message.equals("")) {
                respond(message);
            }
        }
    }
}
