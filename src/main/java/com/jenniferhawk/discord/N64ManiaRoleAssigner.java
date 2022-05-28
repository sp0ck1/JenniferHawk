package com.jenniferhawk.discord;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class N64ManiaRoleAssigner extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {

        if (event.getMessageId().equals("723160483749691502")) { // N64Mania Hey role
            User user = event.getUser();
            Role hey = event.getGuild().getRoleById("723159374654210108");

                if (event.getReaction().getReactionEmote().getId().equals("675022837613854777")) {
                    event.getGuild().addRoleToMember(user.getId(), hey).complete();
                }
            }
        }


    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        if (event.getMessageId().equals("723160483749691502")) { // N64Mania Hey role
            User user = event.getUser();
            Role hey = event.getGuild().getRoleById("723159374654210108");
            if (event.getReactionEmote().getId().equals("675022837613854777")) {
                event.getGuild().removeRoleFromMember(user.getId(), hey).complete();
            }
        }
    }
}
