package com.jenniferhawk.howlongtobeat;

/**
 * Service interface for interaction with <a href="http://howlongtobeat.com">Howlongtobeat</a>.
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public interface HLTBService {

	String ERROR_HLTB_GONE = "HLTB_GONE";
	String HLTB_URL = "http://www.howlongtobeat.com/";

	/**
   * Searches for a given string, parsing the result as {@link HLTBSearchResultPage}
	 * @param gameName
	 * @return
	 */
	HLTBSearchResultPage search(String gameName);

	/**
   * Get the details for a specific game, represented by an id (as used by <a href="http://howlongtobeat.com">Howlongtobeat</a>)
	 * @param gameId the id used by <a href="http://howlongtobeat.com">Howlongtobeat</a>, like 16624 or 35878
	 * @return a detail entry
	 */
	HLTBEntry detail(String gameId);

}
