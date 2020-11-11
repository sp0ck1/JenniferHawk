package com.jenniferhawk.howlongtobeat;

import static com.jenniferhawk.howlongtobeat.HowLongToBeatUtil.calculateSearchHitPropability;
import static com.jenniferhawk.howlongtobeat.HowLongToBeatUtil.parseTime;
import static com.jenniferhawk.howlongtobeat.HowLongToBeatUtil.parseTypeAndSet;

import java.util.ArrayList;
import java.util.HashSet;
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
public class HLTBTestSearchResultPage {

    private final String htmlFragment;
    private final String searchTerm;
    private int resultCount = -1;
    private List<HLTBSearchResultEntry> entries;

    public HLTBTestSearchResultPage(String term, String fragment) {
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
            handleResultList(html);
        } else {
            handleNoResult();
        }
    }

    private void handleResult(Document html) {
        Elements liElements = html.getElementsByTag("li");
        this.resultCount = liElements.size();
        //System.out.println("Result count: " + this.resultCount);
        Set<HLTBSearchResultEntry> entrySet = new HashSet<>();

//				liElements	.stream()
//																	.map(element -> handleHltbResultLi(element))
//																	.collect(Collectors.toSet());

        for (Element element : liElements) {
            entrySet.add(handleHltbResultLi(element));
        }
        this.entries = new ArrayList<>(entrySet); // Randomized ordering TODO: Create non-randomized ordering
    }

    private void handleResultList(Document html) {
        Elements liElements = html.getElementsByTag("li");
        this.resultCount = liElements.size();
        // System.out.println("Result count: " + this.resultCount);
        List<HLTBSearchResultEntry> entryList = new ArrayList<>();

//				liElements	.stream()
//																	.map(element -> handleHltbResultLi(element))
//																	.collect(Collectors.toSet());

        for (Element element : liElements) {
            entryList.add(handleHltbResultLi(element));
        }
        this.entries = new ArrayList<>(entryList); // Randomized ordering TODO: Test to get the same result every time
    }

    private HLTBSearchResultEntry handleHltbResultLi(Element liElement) { // This one parses the search results page
        String type = "";
        String time = "0";
        Element gameTitle = liElement.getElementsByTag("a")
                .get(0);
        HLTBSearchResultEntry entry = new HLTBSearchResultEntry();

        entry.setName(gameTitle.attr("title"));
        String href = HLTBService.HLTB_URL + gameTitle.attr("href");
        entry.setDetailLink(href);
        entry.setGameId(href.substring(href.indexOf("?id=") + 4));
        entry.setImageSource(gameTitle.getElementsByTag("img")
                .get(0)
                .attr("src"));

        //entry.setPropability(calculateSearchHitPropability(entry.getName(), searchTerm));
        Elements detailsBlockElements = liElement.getElementsByClass("search_list_details_block");
        if (detailsBlockElements.size() != 0) {

            int childrenSize = detailsBlockElements.get(0).children().size();

            if (childrenSize == 1) { // A typical game will only have one child
                Element firstChild = detailsBlockElements.get(0)
                        .children().first();

                System.out.println("firstChild has this many children: " + firstChild.children().size());
                if (firstChild.children().size() != 0) {

                    type = firstChild.child(0)
                            .text();

                    time = parseTime(firstChild.child(1) // The second in the list of divs. 0 is Main Story/Solo, 1 is the time
                            .text()).toLowerCase();
                    System.out.println("Setting time equal to " + time);
                    parseTypeAndSet(entry, type, time);

                }
                return entry;

            } else {

                type = detailsBlockElements.get(0).child(0).text();

                time = parseTime(detailsBlockElements.get(0).child(1).text()).toLowerCase();
                System.out.println("Setting time equal to " + time);
                parseTypeAndSet(entry, type, time);
            }
        } else {
            System.out.println("Setting time to 0");
            parseTypeAndSet(entry, "Main Story", "0");
        }
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
