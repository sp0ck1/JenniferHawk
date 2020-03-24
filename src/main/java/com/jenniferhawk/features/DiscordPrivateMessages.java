package com.jenniferhawk.features;

import com.jenniferhawk.vimmparser.VimmN64;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class DiscordPrivateMessages extends ListenerAdapter {

    VimmN64 vimm;
    String message;

    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        super.onPrivateMessageReceived(event);
        User author = event.getAuthor();
        String original = event.getMessage().getContentDisplay();

        if (original.toLowerCase().startsWith("!gamestop")) {
            String searchTerm = original.substring(original.lastIndexOf("!gamestop") + 10);
            System.out.println("Search term is: " + searchTerm);

            vimm = new VimmN64(searchTerm);
            vimm.searchVimm();
            if (vimm.hasResults()) {
                vimm.getResults();
                String download = vimm.getDownload();
                String otherDownload = vimm.getOtherDownload();
                String fileName = vimm.getFileName();
                String otherFileName = vimm.getOtherFileName();
                message = fileName + ": " + download + " \n" + otherFileName + ": " + otherDownload + ". ";

                author.openPrivateChannel().queue((privateChannel) -> privateChannel.sendMessage(message).queue());
            } else {
                message = "Couldn't find " + searchTerm + " on Vimm. Try it with any missing apostrophes or punctuation. If it's a long name, like Xena: Warrior Princess: The Talisman of Fate, try searching with a keyword, like \"Xena\"";
                author.openPrivateChannel().queue((privateChannel) -> privateChannel.sendMessage(message).queue());
            }
        } else if (original.toLowerCase().startsWith("!tellthem")) {
            String message = original.substring(9);
            TextChannel channel =  event.getJDA().getTextChannelById("673055022501855253");
            assert channel != null;
            channel.sendMessage(message).queue();
        }
    }
}



