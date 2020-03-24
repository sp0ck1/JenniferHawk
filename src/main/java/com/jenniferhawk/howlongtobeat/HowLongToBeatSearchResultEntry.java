package com.jenniferhawk.howlongtobeat;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class HowLongToBeatSearchResultEntry extends HowLongToBeatEntry {

	private double propability = 0;

	/**
	 * @return the propability as float, representing the propability for an
	 *         exact match
	 */
	public double getPropability() {
		return propability;
	}

	/**
	 * @param propability
	 *            the propability to set
	 */
	public void setPropability(double propability) {
		this.propability = propability;
	}

}
