package com.jenniferhawk.howlongtobeat;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.http.MediaType;
//import io.micrometer.core.annotation.Timed;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * ServiceImplementation for calls against
 * <a href="http://howlongtobeat.com/">howlongtobeat.com</a>. Great website,
 * great service.
 *
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class HLTBServiceDefaultImpl implements HLTBService {

  private static final String HLTB_SEARCH_URL = "https://howlongtobeat.com/search_results?page=1";
  private static final String HLTB_DETAIL_URL = "https://howlongtobeat.com/game.php";

  @Override
   public HLTBSearchResultPage search(String gameName) {
    HttpResponse<String> response;
    try {
      response = Unirest.post(HLTB_SEARCH_URL).header("accept", "*/*")
          .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE).queryString("page", "1")
          .field("queryString", gameName).field("t", "games").field("sorthead", "popular")
          .field("sortd", "Normal Order").field("plat", "").field("length_type", "main").field("length_min", "")
          .field("length_max", "").field("detail", "0").asString();
      return new HLTBSearchResultPage(gameName, response.getBody());
    } catch (UnirestException e) {
      throw new ContextedRuntimeException("Howlongtobeat not available", e).addContextValue("errorId", ERROR_HLTB_GONE)
          .addContextValue("gameName", gameName).addContextValue("url", HLTB_SEARCH_URL);
    }
  }

  @Override
  public HLTBEntry detail(String gameId) { // FIXME: this might be able to be deleted
//    HttpResponse<String> response;
//    try {
//      response = Unirest.get(HLTB_DETAIL_URL).header("accept", "text/html").queryString("id", gameId).asString();
//      HowLongToBeatDetailPage detailPage = new HowLongToBeatDetailPage(response.getBody(),
//          HLTB_DETAIL_URL + "?id=" + gameId, gameId);
//      System.out.println("Does this actually get used?");
//      return detailPage.getEntry();
//    } catch (UnirestException e) {
//      throw new ContextedRuntimeException("Howlongtobeat not available", e).addContextValue("errorId", ERROR_HLTB_GONE)
//          .addContextValue("gameId", gameId).addContextValue("url", HLTB_DETAIL_URL);
//    }
    return null;
  }

}
