package com.JenniferHawk.features;

import com.JenniferHawk.VimmParser.VimmN64;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class DiscordPrivateMessages extends ListenerAdapter {

    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        super.onPrivateMessageReceived(event);
        User author = event.getAuthor();
        String original = event.getMessage().getContentDisplay();
        
        if (original.toLowerCase().startsWith("!gamestop"))
        {
            String searchTerm = original.substring(original.lastIndexOf("!gamestop") + 10);
            System.out.println("Search term is: " + searchTerm);

            VimmN64 vimm = new VimmN64(searchTerm);
            String link = vimm.getLink();
            String download = vimm.getDownload();
            String otherDownload = vimm.getOtherDownload();
            String fileName = vimm.getFileName();
            String otherFileName = vimm.getOtherFileName();

            author.openPrivateChannel().queue((privateChannel) -> {privateChannel.sendMessage(fileName + ": " + download + " \n" + otherFileName + ": " + otherDownload + ". ").queue();});
            //channel.sendMessage(link).queue();
        }
    }
}
