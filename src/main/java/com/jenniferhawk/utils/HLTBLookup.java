package com.jenniferhawk.utils;

import com.jenniferhawk.howlongtobeat.HLTBEntry;
import com.jenniferhawk.howlongtobeat.HLTBService;
import com.jenniferhawk.howlongtobeat.HLTBServiceDefaultImpl;
import com.jenniferhawk.howlongtobeat.HLTBSearchResultPage;

public class HLTBLookup {

    private static HLTBService service = new HLTBServiceDefaultImpl();

    public static HLTBEntry searchGame(String game) {

        HLTBSearchResultPage result = new HLTBSearchResultPage(game, service.search(game).getHtmlFragment());

        if (result.getEntries().size() != 0) { // Search returned something
            HLTBEntry entry = result.getEntries().get(0); // Get the first HLTBEntry and hope it's the correct one.

            String HLTB = entry.getMainStoryTime();
            String hltbGame = entry.getName();

            return entry;
        }
        else {

            return null; // Search returned no results
        }
    }
}
