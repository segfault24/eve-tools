package atsb.eve.model;

public class MarketStat {

	private int regionId;
	private int typeId;
	private long ma30;
	private long ma90;

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public long getMa30() {
		return ma30;
	}

	public void setMa30(long ma30) {
		this.ma30 = ma30;
	}

	public long getMa90() {
		return ma90;
	}

	public void setMa90(long ma90) {
		this.ma90 = ma90;
	}

}
