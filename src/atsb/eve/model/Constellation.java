package atsb.eve.model;

public class Constellation {

	private int constellationId;
	private String constellationName;
	private int regionId;

	public Constellation() {
	}

	public int getConstellationId() {
		return constellationId;
	}

	public void setConstellationId(int constellationId) {
		this.constellationId = constellationId;
	}

	public String getConstellationName() {
		return constellationName;
	}

	public void setConstellationName(String constellationName) {
		this.constellationName = constellationName;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

}
