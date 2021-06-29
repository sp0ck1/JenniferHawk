package com.jenniferhawk.speedrunslive;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class RaceResultJSONGrabber {

    private final String jsonResult;

    public RaceResultJSONGrabber(String raceID) {

        String SRL_RACE_URL = "https://api.speedrunslive.com/pastraces/" + raceID;

        HttpResponse<String> response;
        try {
            response = Unirest.get(SRL_RACE_URL)
            .asString();
            this.jsonResult = response.getBody().substring(response.getBody().indexOf(
                    "\"results\" :\n"+
                    "[\n"+
                    "{")+12,
                    response.getBody().indexOf("}],")+2);
            //System.out.println("Grabbed JSON result of race " + raceID);
            System.out.println(jsonResult);
        } catch (
                UnirestException e) {
            throw new ContextedRuntimeException("Sp0ck1 SRL Placeholder error", e).addContextValue("errorId", "SRL BEING WEIRD");
        }
    }

    public String getJsonResult() {
        return this.jsonResult;
    }
}
