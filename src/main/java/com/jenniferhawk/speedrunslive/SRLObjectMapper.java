package com.jenniferhawk.speedrunslive;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class SRLObjectMapper {


    public SRLResultList mapRaceResultJSON(String raceID) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String jsonArrayFromURL = new RaceResultJSONGrabber(raceID).getJsonResult();

        List<SRLRaceResultEntry> srlRaceResultEntryList;
        srlRaceResultEntryList = objectMapper.readValue(jsonArrayFromURL ,new TypeReference<List<SRLRaceResultEntry>>(){});

        // Example of returning a new SRLResultList as the end result of some method that takes a race ID as an input
        // Also example of how to get a random entry; do not actually return all of this as one output. Example purposes only.
        // Make sure that the entry returned actually has a comment.
        return new SRLResultList(srlRaceResultEntryList);




    }
}
