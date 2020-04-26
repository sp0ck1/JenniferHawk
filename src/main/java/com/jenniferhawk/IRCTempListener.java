package com.jenniferhawk;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class IRCTempListener extends ListenerAdapter {
    @Override
    public void onGenericMessage(GenericMessageEvent event) {
        System.out.println(event.getMessage());

    }
}
