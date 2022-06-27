package com.jenniferhawk.n64mania;

import com.jenniferhawk.speedrunslive.SRLObjectMapper;
import com.jenniferhawk.speedrunslive.SRLRaceEntrant;
import com.jenniferhawk.speedrunslive.SRLRaceEntrantList;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.api.RacetimeApi;
import org.openapitools.client.model.Race;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@NoArgsConstructor
public class N64ManiaCommentRetrievalTool {

    Random random = new Random();

    @NonNull
    private N64ManiaComment getCommentFromSRL(String srlRaceID, String gameName) throws IOException {
        ArrayList<N64ManiaComment> commentList = new ArrayList<>();
        System.out.println("getCommentFromSRL. srlRaceID: " + srlRaceID + " for game " + gameName);
        SRLRaceEntrantList srlRaceEntrantList = new SRLObjectMapper().mapRaceResultJSON(srlRaceID);

            if (srlRaceEntrantList != null) { // If no entrants, return null
                List<SRLRaceEntrant> entries = srlRaceEntrantList.getEntrants();

                entries.forEach(srlRaceEntrant -> {
                    System.out.println("For each srlRaceEntrant, did they comment? Add entrant to commentList if so.");
                    if (!(srlRaceEntrant.getComment() == null)) {
                        System.out.println("has_srl_comment:true");
                        N64ManiaComment newComment = new N64ManiaComment(gameName, srlRaceEntrant.getComment(), srlRaceEntrant.getPlayer());
                        commentList.add(newComment);
                    }
                });
            } else return null;

            if (commentList.size() != 0) {
                return commentList.get(random.nextInt(commentList.size()));
            } else return null;
    }

    @NonNull
    private N64ManiaComment getCommentFromRacetime(String slug) {
        ArrayList<N64ManiaComment> commentList = new ArrayList<>();

        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://racetime.gg");
        RacetimeApi apiInstance = new RacetimeApi(defaultClient);


        String category = "n64mania";

        try {
            Race race = apiInstance.getRaceDetails(category,slug);

            race.getEntrants().forEach(entrant -> {
                if (entrant.getHasComment()) {
                    commentList.add(new N64ManiaComment(race.getInfo(),entrant.getComment(),entrant.getUser().getName()));
                }

            });
        } catch (ApiException e) {
            System.err.println("Exception when calling RacetimeApi#getRaceDetails");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        System.out.println("Is commentList.size >= 0? " + commentList.size());
        if (commentList.size() != 0) {
            return commentList.get(random.nextInt(commentList.size()));
        } else return null;

    }
    private String getRacetimeSlug(String raceURL)  {
        return raceURL.substring(raceURL.indexOf("n64mania/") + 9);
    }

    private String getSRLRaceID(String raceURL) {
       return raceURL.substring(raceURL.length() - 6);
    }

    public N64ManiaComment getRandomComment(N64Game n64Game) {
        N64ManiaComment comment = null;
        if (n64Game.getRaceURL().startsWith("https://racetime")) {
            return getCommentFromRacetime(getRacetimeSlug(n64Game.getRaceURL()));

        } else if (n64Game.getRaceURL().startsWith("https://www.speedrunslive")) {
            try {
                comment = getCommentFromSRL(getSRLRaceID(n64Game.getRaceURL()),n64Game.getTitle());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return comment;

        }

        else return null;

    }
    }

