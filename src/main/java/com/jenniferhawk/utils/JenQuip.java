package com.jenniferhawk.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JenQuip {

    public String getQuip(String game) {
        return jenQuip(game);
    }

    private String jenQuip(String game) {
        Random random = new Random();
        List<String> quipSet = new ArrayList<String>();
        quipSet.add("I wouldn't have chosen that one, but to each their own.");
        quipSet.add("That's on you, bud.");
        quipSet.add("This looks like trash, but what do I know?");
        quipSet.add("Do you own that one?");
        quipSet.add("Do you really want that on your record?");
        quipSet.add("Have you checked to see if there's a Gamecube version instead?");
        quipSet.add("I guess that's what you wanted, right?");
        quipSet.add("This could be good, I guess?");
        quipSet.add("Spock rented this once. He said it was fine.");
        quipSet.add("May Blockbuster bless your soul.");
        quipSet.add("Eat your heart out, Mario.");
        quipSet.add("Somehow, this one slipped by Miyamoto.");
        quipSet.add("Fancy! Or terrible, depending upon the circumstances.");
        quipSet.add("Can Flant farm us on this one?");
        quipSet.add("\u00af\\\\\\\u005f\u0028\u30c4\u0029\\\u005f\u002f\u00af");
        quipSet.add("It's no Pilotwings but, I guess so.");
        quipSet.add("I pity the fool who rolled this game.");
        quipSet.add("You know it's totally okay if you want to reroll this one, right?");
        quipSet.add("The beginning of this game, it's good. But that end? What an end!");
        quipSet.add("I think that this one is really only good in the midgame. Just my opinion.");
        quipSet.add("I played this with the birds last week. Pretty good.");
        quipSet.add("I don't know, what if you put this in Spocktober instead?");
        quipSet.add("Maybe for next Gamerade, I don't know.");
        quipSet.add("Not quite right now, but there may be a surprise for you in your stocking!");
        quipSet.add("You might have to USE some BS to get through this one. Maybe, maybe not.");
        quipSet.add("Have you played or checked out Pizza Tower instead? I don't know if that's really your type of thing, but it is a platformer and--");


        if (game.equalsIgnoreCase("Pilotwings 64")) {
            return "That's a damn fine video game.";
        } else if (
                game.equalsIgnoreCase("Gauntlet Legends")) {
            return "@Hydromedia.";
        } else {

            return quipSet.get(random.nextInt(quipSet.size()));
        }
    }
}
