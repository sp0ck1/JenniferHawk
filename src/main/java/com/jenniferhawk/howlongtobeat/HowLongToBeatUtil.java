package com.jenniferhawk.howlongtobeat;

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
	public static HLTBEntry parseTypeAndSet(HLTBEntry entry, String type, String time) { // I only actually need to parse this if there's a " - " in it.
		if (type.startsWith("Main Story") || type.startsWith("Single-Player") || type.startsWith("Solo")) {
			entry.setMainStory(time);
		} else if (type.startsWith("Main + Extra")) {
			entry.setMainAndExtra(Double.parseDouble(time));
		} else if (type.startsWith("Completionist")) {
			entry.setCompletionist(Double.parseDouble(time));
		} else if (type.startsWith("Co-Op")) {
			entry.setCoop(Double.parseDouble(time));
		} else if (type.startsWith("Vs.")) {
			entry.setVs(Double.parseDouble(time));
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
	 * @return the time as 0, or a String containing a number and timeunit
	 */
	public static String parseTime(String text) {
		// "65&#189; Hours"; "--" if not known
		if (text.equals("--")) {
			return "0";
		}
		if (text.contains(" - ")) {
			return handleRange(text);
		}
		// System.out.println("parseTime returning " + text);
		return getTime(text);
	}

	/**
	 * @param text
	 *            like '5 Hours - 12 Hours' or '2½ Hours - 33½ Hours'
	 * @return
	 */
	private static String handleRange(String text) {
		System.out.println("Time text is: " + text);
		String timeUnit = text.substring(text.indexOf(" "), text.indexOf(" - "));
		String[] range = text.split(" - ");
		double from = Double.parseDouble(getTime(range[0]));
		double to = Double.parseDouble(getTime(range[1]));
		double d = from + to / 2; // Take FROM (hours) and TO (hours) range, add together and divide by two for AVG
		return d +" "+timeUnit;
	}

	/**
	 * @param text,
	 *            can be '12 Hours' or '5½ Hours'
	 * @return
	 */
	private static String getTime(String text) {
		String time = text.substring(0, text.indexOf(" "));
		String timeUnit = text.substring(text.indexOf(" "));
		if (timeUnit.toLowerCase().equals("mins")) {
			timeUnit = "Minutes";
		}
		//System.out.println("What unit are we talking, here? " + timeUnit.trim());

//		if (time.contains("\u00BD")) { // ½
//			double parsedHalf = 0.5 + Double.parseDouble(time.substring(0, text.indexOf("\u00BD")));
//			return String.valueOf(parsedHalf);
//		}
		return time+timeUnit;
	}
}
