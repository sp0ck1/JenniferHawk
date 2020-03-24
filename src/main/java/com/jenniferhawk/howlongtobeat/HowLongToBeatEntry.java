package com.jenniferhawk.howlongtobeat;

/**
 * Encapsulates a single entry with all relevant information
 *
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class HowLongToBeatEntry {

	private String name;
	private String detailLink;
	private String gameId;
	private String imageSource;
	private double mainStory;
	private double mainAndExtra;
	private double completionist;
	private double vs;
	private double coop;

	/**
	 * @return the name of the game
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the detailLink as used in <a href="http://howlongtobeat.com">Howlongtobeat</a>
	 */
	public String getDetailLink() {
		return detailLink;
	}

	/**
	 * @param detailLink
	 *            the detailLink to set
	 */
	public void setDetailLink(String detailLink) {
		this.detailLink = detailLink;
	}

	/**
	 * @return the imageSource of the cover image
	 */
	public String getImageSource() {
		return imageSource;
	}

	/**
	 * @param imageSource
	 *            the imageSource to set
	 */
	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}

	/**
	 * @return the amount of hours to play the main story
	 */
	public double getMainStory() {
		return mainStory;
	}

	/**
	 * @param the amount of hours to play the main story
	 */
	public void setMainStory(double mainStory) {
		this.mainStory = mainStory;
	}

	/**
	 * @return the amount of hours to play the main story plus some extras
	 */
	public double getMainAndExtra() {
		return mainAndExtra;
	}

	/**
	 * @param the amount of hours to play the main story plus some extras
	 */
	public void setMainAndExtra(double mainAndExtra) {
		this.mainAndExtra = mainAndExtra;
	}

	/**
	 * @return the amount of hours to 100% complete the game
	 */
	public double getCompletionist() {
		return completionist;
	}

	/**
	 * @param the amount of hours to 100% complete the game
	 */
	public void setCompletionist(double completionist) {
		this.completionist = completionist;
	}
	


	/**
	 * @return the vs
	 */
	public double getVs() {
		return vs;
	}

	/**
	 * @param vs the vs to set
	 */
	public void setVs(double vs) {
		this.vs = vs;
	}

	/**
	 * @return the coop
	 */
	public double getCoop() {
		return coop;
	}

	/**
	 * @param coop the coop to set
	 */
	public void setCoop(double coop) {
		this.coop = coop;
	}

	/**
	 * @return the gameId
	 */
	public String getGameId() {
		return gameId;
	}

	/**
	 * @param gameId
	 *            the gameId to set
	 */
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	@Override
	public String toString() {
		return "HowLongToBeatEntry [name=" + name + ", detailLink=" + detailLink + ", gameId=" + gameId
				+ ", imageSource=" + imageSource + ", mainStory=" + mainStory + ", mainAndExtra=" + mainAndExtra
				+ ", completionist=" + completionist + ", vs=" + vs + ", coop=" + coop + "]";
	}



}
