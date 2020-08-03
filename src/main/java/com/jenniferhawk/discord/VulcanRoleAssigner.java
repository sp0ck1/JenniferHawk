package com.jenniferhawk.discord;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class VulcanRoleAssigner extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {

        if (event.getMessageId().equals("737279981540999268")) { // Vulcan PingMe message
            User user = event.getUser();
            Role pingMe = event.getGuild().getRoleById("737267159926833212"); // PingMe role

            if (event.getReactionEmote().getId().equals("690167711182880813")) { // sp0ck1Bread
                event.getGuild().addRoleToMember(user.getId(), pingMe).complete();
            }
        }
    }


    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        if (event.getMessageId().equals("737279981540999268")) { // Vulcan PingMe message
            User user = event.getUser();
            Role pingMe = event.getGuild().getRoleById("737267159926833212"); // PingMe role

            if (event.getReactionEmote().getId().equals("690167711182880813")) { // sp0ck1Bread
                event.getGuild().removeRoleFromMember(user.getId(), pingMe).complete();
            }
        }
    }
}
