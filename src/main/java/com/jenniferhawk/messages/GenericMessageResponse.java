package com.jenniferhawk.messages;

import com.github.twitch4j.common.enums.CommandPermission;
import com.jenniferhawk.n64mania.*;
import com.jenniferhawk.database.JenDB;
import com.jenniferhawk.howlongtobeat.HLTBEntry;
import com.jenniferhawk.irc.SRLRaceListener;
import com.jenniferhawk.twitch.ChannelGoLiveCheck;
import com.jenniferhawk.utils.HLTBLookup;
import com.jenniferhawk.utils.JenQuip;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    String[] argumentList;

    @Override
    public GenericCommandResponse setArgumentList(String[] argumentList) {
        this.argumentList = argumentList;
        return this;
    }

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
        N64ManiaAPI n64ManiaAPI = new N64ManiaAPI();

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

                case "title":
                    if (permissionType.contains(CommandPermission.MODERATOR) && sourceChannel.equals("sp0ck1")) {
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
                    message = "Sp0ck1 has made $" + truckMoney + " trucking today. Animals.";
                    break;

                case "rolln64":
                    N64ManiaAPI api = new N64ManiaAPI();
                    JenQuip quip = new JenQuip();
                    String game = api.getRandomGameName();

                    message = user + ", you are responsible for suggesting " +
                                game + ". " + quip.getQuip(game) +
                                ".";

                    break;
                case "runback":
                    String commenter;
                    String comment;
                    String randomCommentPhrase = "";

                    N64ManiaRunback n64ManiaRunback = null;

                    if (messageType == MessageType.DISCORD) {
                        user = user + "_7k";
                    }

                    int counter = 1;
                    boolean tryagain = true;

                    // If a game has no comments, try again until you get a race that has comments.
                    while (tryagain) {
                        System.out.println("Attempt number " + counter + " to draw a game with comments.");

                        n64ManiaRunback = n64ManiaAPI.getRunback();
                        tryagain = (n64ManiaRunback.getComment() == null);
                        counter++;
                    }

                    commenter = n64ManiaRunback.getCommenter();
                    comment = n64ManiaRunback.getComment();
                    String finalCommentChar = String.valueOf(comment.charAt(comment.length() - 1));
                    System.out.println("finalCommentChar is " + finalCommentChar);
                    // If comment does not end in any punctuation, put a period on it.
                    if ((finalCommentChar.equals(".")) ||
                            (finalCommentChar.equals("?")) ||
                            (finalCommentChar.equals("!"))) {
                        // do nothing
                    } else {
                        comment = comment + ".";
                    }
                    randomCommentPhrase = commenter + " had this to say about the game: \"" + comment + "\" ";
            

                    //--
                    message = user + ", would you suggest doing a runback of " +
                            n64ManiaRunback.getGame() + "? " +
                            n64ManiaRunback.getWinner() + " won this race originally. " +
//                            "Personally, I'd give it a " + rating +
//                            " out of " + outOf + ". " +
                              randomCommentPhrase;
//                            "It was a " + adjectives[random.nextInt(adjectives.length)] +
//                            " one, " + finishers[random.nextInt(finishers.length)] + ".";
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
                    message = n64ManiaAPI.getCommandResponse(command);
            }
            if (!message.equals("")) {
                respond(message);
            }
        }
   }
}
