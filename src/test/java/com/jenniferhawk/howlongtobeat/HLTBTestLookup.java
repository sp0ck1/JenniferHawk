package com.jenniferhawk.howlongtobeat;

public class HLTBTestLookup {

    private static HLTBService service = new HLTBServiceDefaultImpl();


    public static HLTBEntry searchGame(String game) {

        HLTBTestSearchResultPage result = new HLTBTestSearchResultPage(game, service.search(game).getHtmlFragment());
        System.out.println("Fragment: " + result.getHtmlFragment());
        if (result.getEntries().size() != 0) { // Search returned something


            return result.getEntries().get(0); // Return the first HLTBEntry
        }
        else {
            System.out.println("The search returned no results");
            return null; // Search returned no results
        }
    }
}
