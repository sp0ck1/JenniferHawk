package com.jenniferhawk.speedrunslive;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class SRLObjectMapper {


    public SRLRaceEntrantList mapRaceResultJSON(String raceID) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonArrayFromURL = new RaceResultJSONGrabber(raceID).getJsonResult();
        System.out.println("The jsonArray is: " + jsonArrayFromURL);
        if (jsonArrayFromURL != null) {
            List<SRLRaceEntrant> srlRaceEntrantList;
            srlRaceEntrantList = objectMapper.readValue(jsonArrayFromURL, new TypeReference<List<SRLRaceEntrant>>() {
            });

            return new SRLRaceEntrantList(srlRaceEntrantList);
        } else return null;
    }
}
