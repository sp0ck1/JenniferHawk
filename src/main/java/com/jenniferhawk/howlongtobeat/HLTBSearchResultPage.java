package com.jenniferhawk.howlongtobeat;

import static com.jenniferhawk.howlongtobeat.HowLongToBeatUtil.calculateSearchHitPropability;
import static com.jenniferhawk.howlongtobeat.HowLongToBeatUtil.parseTime;
import static com.jenniferhawk.howlongtobeat.HowLongToBeatUtil.parseTypeAndSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Encapsulates the responseBody from an embedded search from
 * <a href="http://howlongtobeat.com">Howlongtobeat</a> (html fragment). If
 * requested, the fragment is analyzed and destructured. See
 * <b>src/test/resources/howlongtobeat/empty|multi|single.html</b> for sample
 * result fragments.
 *
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class HLTBSearchResultPage {

	private final String htmlFragment;
	private final String searchTerm;
	private int resultCount = -1;
	private List<HLTBSearchResultEntry> entries;

	public HLTBSearchResultPage(String term, String fragment) {
		this.searchTerm = term;
		this.htmlFragment = fragment;
	}

	/**
	 * @return the htmlFragment
	 */
	public String getHtmlFragment() {
		return htmlFragment;
	}

	/**
	 * @return the searchTerm that was used
	 */
	public String getSearchTerm() {
		return searchTerm;
	}

	/**
	 * @return the resultCount, 0 if no results, 1 if single result >1 for
	 *         multiple hits
	 */
	public int getResultCount() {
		if (resultCount == -1) {
			analyzeFragment();
		}
		return resultCount;
	}

	/**
	 * @return the entries
	 */
	public List<HLTBSearchResultEntry> getEntries() {
		if (entries == null) {
			analyzeFragment();
		}
		return entries;
	}

	private void analyzeFragment() {
		Document html = Jsoup.parseBodyFragment(htmlFragment);
		if (isResult(html)) {
			handleResult(html);
		} else {
			handleNoResult();
		}
	}

	private void handleResult(Document html) {
		Elements liElements = html.getElementsByTag("li");
		this.resultCount = liElements.size();
		System.out.println("Result count: " + this.resultCount);
		Set<HLTBSearchResultEntry> entrySet = liElements	.stream()
																	.map(element -> handleHltbResultLi(element))
																	.collect(Collectors.toSet());
		this.entries = new ArrayList<>(entrySet);
	}

	private HLTBSearchResultEntry handleHltbResultLi(Element liElement) { // This one parses the search results page
		Element gameTitle = liElement	.getElementsByTag("a")
										.get(0);
		HLTBSearchResultEntry entry = new HLTBSearchResultEntry();
		System.out.println("gameTitle? " + gameTitle);
		entry.setName(gameTitle.attr("title"));
		String href = HLTBService.HLTB_URL + gameTitle.attr("href");
		entry.setDetailLink(href);
		entry.setGameId(href.substring(href.indexOf("?id=") + 4));
		entry.setImageSource(gameTitle	.getElementsByTag("img")
																		.get(0)
																		.attr("src"));
		System.out.println("I just set: " + gameTitle	.getElementsByTag("img")
				.get(0)
				.attr("src"));
		//entry.setPropability(calculateSearchHitPropability(entry.getName(), searchTerm));
		Elements times = liElement.getElementsByClass("search_list_details_block");
		times	.get(0)
				.children()
				.iterator()
				.forEachRemaining((div) -> {
					String type;
					String time;
					if(div.child(0).text() != null) { // Possible fix for NPE here?
						type = div.child(0)
								.text();

					time = parseTime(div	.child(1)
												.text()).toLowerCase();}
					else {
						type = "";
						time = "0";
					}
					parseTypeAndSet(entry, type, time);
					//System.out.println("The entry is " + entry);
				});

		return entry;
	}

	private void handleNoResult() {
		this.resultCount = 0;
		this.entries = new ArrayList<>();

	}

	private boolean isResult(Document html) {
		Elements searchResultHeadline = html.getElementsByTag("h3");
		return searchResultHeadline.size() > 0;
	}

}
