package com.jenniferhawk.features;

import com.jenniferhawk.layout.Utils;
import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;


public class WriteChatToFile {

    File chatlog = new File("D:/JenniferUtils/Chatlog.csv");


    /**
     * Register events of this class with the EventManager
     *
     * @param eventManager EventManager
     */
    public WriteChatToFile(EventManager eventManager) {
        eventManager.getEventHandler(SimpleEventHandler.class).onEvent(ChannelMessageEvent.class, event -> {
            try {
                onChannelMessage(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Subscribe to the ChannelMessage Event and write the output to the csv
     */

    public void onChannelMessage(ChannelMessageEvent event) throws IOException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String message = String.format(
                "\"%s\",\"%s\",\"%s\",\"%s\"\r\n",
                event.getChannel().getName(),
                event.getUser().getName(),
                event.getMessage(),
                timestamp.toString()
        );

        FileUtils.writeStringToFile(chatlog,message,"UTF-8",true);

    }
}
