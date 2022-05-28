package com.jenniferhawk.discord;

import com.jenniferhawk.database.JenDB;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;

import static com.jenniferhawk.Bot.discordClient;

public class VulcanRoleManager extends ListenerAdapter {
    // When a MessageReactionRemove or MessageReactionAdd comes in, it's going to have to look at the DB of messages tied to emotes
//        get Message ID
//        If Message ID in table resultsList,
//        If event.getReactionEmote matches the ReactionEmote assigned to that Reaction
//        get that role by ID and assign or remove the role


    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        String msgid = event.getMessageId();

        if (JenDB.getRoleMessages().contains(msgid)) {
            if (event.getReactionEmote().getId().equals(JenDB.getReactionEmoteIdForMessageId(msgid))) {

            }
            User user = event.getUser();
            Role assignable = event.getGuild().getRoleById(""); //TODO: put in real role ID
        }
        if (msgid.equals("737279981540999268")) { // Vulcan PingMe message
            User user = event.getUser();
            Role pingMe = event.getGuild().getRoleById("737267159926833212"); // PingMe role

            if (event.getReactionEmote().getId().equals("690167711182880813")) { // sp0ck1Bread
                event.getGuild().addRoleToMember(user.getId(), pingMe).complete();
            }
        }
    }


    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        String msgid = event.getMessageId();

        if (event.getMessageId().equals("737279981540999268")) { // Vulcan PingMe message
            User user = event.getUser();
            Role pingMe = event.getGuild().getRoleById("737267159926833212"); // PingMe role

            if (event.getReactionEmote().getId().equals("690167711182880813")) { // sp0ck1Bread
                event.getGuild().removeRoleFromMember(user.getId(), pingMe).complete();
            }
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Random random = new Random();
        Message msg = event.getMessage();
        if (msg.getContentStripped().startsWith("!createrole")) {
            Emote reactionEmote = msg.getEmotes().isEmpty() ? null : msg.getEmotes().get(0);
            String roleName = msg.getContentRaw().split(" ")[1];
            String newRoleId = event.getGuild().createRole()
                .setName(roleName)
                .setColor(new Color(random.nextInt(200),random.nextInt(200),random.nextInt(200)))
                .setMentionable(true).complete().getId();
            JenDB.insertRole(newRoleId, roleName, reactionEmote.getId());

            event.getChannel().sendMessage("React with " + reactionEmote + " to acquire the " + roleName + " role! " +
                    "Remove your reaction to remove the role.").queue(message -> {
                        JenDB.addRoleMessage(message.getId());
            });

        }
    }
}
