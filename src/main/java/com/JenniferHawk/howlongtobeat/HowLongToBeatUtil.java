package com.JenniferHawk.howlongtobeat;

import org.apache.commons.lang3.StringUtils;

/**
 * Utility class
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class HowLongToBeatUtil {

	/**
	 * Calculates the similarity between to strings using Levenshtein in
	 * comparison to the string length. The similarity is calculated as double,
	 * 2 decimal digits, so it will result in a clean percentage.
	 * 
	 * @param name
	 * @param searchTerm
	 * @return
	 */
	public static double calculateSearchHitPropability(String name, String searchTerm) {
		String longer = name.toLowerCase(), shorter = searchTerm.toLowerCase();
		if (longer.length() < shorter.length()) { // longer should always have
													// greater length
			longer = searchTerm;
			shorter = name;
		}
		int longerLength = longer.length();
		if (longerLength == 0) {
			return 1.0;
		}
		return ((double) Math.round(
				((longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) / (double) longerLength) * 100)
				/ 100);
	}

	/**
	 * Parses the text as type (Main Story, Vs., Coop., ...) and sets the
	 * corresponding attribute in given HowLongToBeatEntry
	 * 
	 * @param entry
	 * @param type
	 * @param time
	 * @return
	 */
	public static HowLongToBeatEntry parseTypeAndSet(HowLongToBeatEntry entry, String type, double time) {
		if (type.startsWith("Main Story") || type.startsWith("Single-Player") || type.startsWith("Solo")) {
			entry.setMainStory(time);
		} else if (type.startsWith("Main + Extra")) {
			entry.setMainAndExtra(time);
		} else if (type.startsWith("Completionist")) {
			entry.setCompletionist(time);
		} else if (type.startsWith("Co-Op")) {
			entry.setCoop(time);
		} else if (type.startsWith("Vs.")) {
			entry.setVs(time);
		}
		return entry;
	}

	/**
	 * Utility method used for parsing a given input text (like
	 * &quot;44&#189;&quot;) as double (like &quot;44.5&quot;). The input text
	 * represents the amount of hours needed to play this game.
	 * 
	 * @param text
	 *            representing the hours
	 * @return the pares time as double
	 */
	public static double parseTime(String text) {
		// "65&#189; Hours"; "--" if not known
		if (text.equals("--")) {
			return 0;
		}
		if (text.indexOf(" - ") > -1) {
			return handleRange(text);
		}
		return getTime(text);
	}

	/**
	 * @param text
	 *            like '5 Hours - 12 Hours' or '2½ Hours - 33½ Hours'
	 * @return
	 */
	private static double handleRange(String text) {
		String[] range = text.split(" - ");
		double d = (getTime(range[0]) + getTime(range[1])) / 2; // Take FROM (hours) and TO (hours) range, add together and divide by two for AVG
		return d;
	}

	/**
	 * @param text,
	 *            can be '12 Hours' or '5½ Hours'
	 * @return
	 */
	private static double getTime(String text) {
		String time = text.substring(0, text.indexOf(" "));

		if (time.contains("\u00BD")) {
			return 0.5 + Double.parseDouble(time.substring(0, text.indexOf("\u00BD")));

		}
		return Double.parseDouble(time);
	}
}
