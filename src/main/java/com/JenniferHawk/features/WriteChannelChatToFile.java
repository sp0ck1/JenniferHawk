package com.JenniferHawk.features;

import com.github.philippheuer.events4j.EventManager;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.io.File;


public class WriteChannelChatToFile {

    /**
     * Register events of this class with the EventManager
     *
     * @param eventManager EventManager
     */
    public WriteChannelChatToFile(EventManager eventManager) {
        eventManager.onEvent(ChannelMessageEvent.class).subscribe(event -> {
            try {
                onChannelMessage(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Subscribe to the ChannelMessage Event and write the output to the console
     */
//TODO: Separate string into three variables and add to either csv or database instead of .txt,
// keep timestamp and then find method for downloading chatlog where match given date or IS BETWEEN two dates.



    public void onChannelMessage(ChannelMessageEvent event) throws IOException {

        String message = String.format(
                "Channel: %s | %s: %s ham \r\n",
                event.getChannel().getName(),
                event.getUser().getName(),
                event.getMessage()
        );
      //  System.out.println(message);
        //FileWriters writer = new FileWriters();
        //writer.writeChatToFile(message);

    }
}
