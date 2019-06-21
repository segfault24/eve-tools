package atsb.eve.model;

public class Station {

	private int stationId;
	private String stationName;
	private int solarSystemId;
	private int constellationId;
	private int regionId;

	public Station() {
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public int getSolarSystemId() {
		return solarSystemId;
	}

	public void setSolarSystemId(int solarSystemId) {
		this.solarSystemId = solarSystemId;
	}

	public int getConstellationId() {
		return constellationId;
	}

	public void setConstellationId(int constellationId) {
		this.constellationId = constellationId;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

}
