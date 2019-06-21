package atsb.eve.model;

public class SolarSystem {

	private int solarSystemId;
	private int constellationId;
	private int regionId;
	private String solarSystemName;
	private double x;
	private double y;
	private double z;
	private double security;

	public SolarSystem() {
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

	public String getSolarSystemName() {
		return solarSystemName;
	}

	public void setSolarSystemName(String solarSystemName) {
		this.solarSystemName = solarSystemName;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getSecurity() {
		return security;
	}

	public void setSecurity(double security) {
		this.security = security;
	}

}
