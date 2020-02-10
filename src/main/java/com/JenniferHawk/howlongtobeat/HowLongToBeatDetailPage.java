package com.JenniferHawk.howlongtobeat;

import static com.JenniferHawk.howlongtobeat.HowLongToBeatUtil.parseTime;
import static com.JenniferHawk.howlongtobeat.HowLongToBeatUtil.parseTypeAndSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Encapsulates the detailpage. Uses the html used by the webite
 * <a href="http://howlongtobeat.com">Howlongtobeat</a> to represent a single
 * game entry and parses the relevant information.
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class HowLongToBeatDetailPage {

	private final String html;
	private final com.JenniferHawk.howlongtobeat.HowLongToBeatEntry entry;

	/**
	 * Constructs the object
	 * 
	 * @param html
	 *            the markup from
	 *            <a href="http://howlongtobeat.com">Howlongtobeat</a>
	 * @param detailUrl
	 *            the link to this resource from
	 *            <a href="http://howlongtobeat.com">Howlongtobeat</a>
	 * @param gameId
	 *            the id used by
	 *            <a href="http://howlongtobeat.com">Howlongtobeat</a>
	 */
	public HowLongToBeatDetailPage(String html, String detailUrl, String gameId) {
		this.html = html;
		this.entry = analyzeDetailPage(detailUrl, gameId);
	}

	/**
	 * @return the parsed entry
	 */
	public HowLongToBeatEntry getEntry() {
		return this.entry;
	}

	private HowLongToBeatEntry analyzeDetailPage(String detailUrl, String gameId) {
	    System.out.println("Analyzing detail page.");
		final HowLongToBeatEntry entry = new HowLongToBeatEntry();
		Document page = Jsoup.parse(this.html);
		Elements title = page.getElementsByClass("profile_header");
		entry.setName(title.text()); // Get Game Title
		entry.setDetailLink(detailUrl);
		entry.setGameId(gameId);
		Elements liElements = page.select(".game_times > ul > li");;
		liElements	.stream()
					.forEach(li -> {
						String type = li.getElementsByTag("h5") // <h5>Single-Player</h5>
										.get(0)
										.text();

						double time = parseTime(li	.getElementsByTag("div")
													.text());
						parseTypeAndSet(entry, type, time);
					});
		entry.setImageSource(page	.select(".game_image > img")
									.get(0)
									.attr("src"));
		System.out.println("I am analyze details. I set: " + page	.select(".game_image > img")
                .get(0)
                .attr("src"));
		return entry;
	}

}
