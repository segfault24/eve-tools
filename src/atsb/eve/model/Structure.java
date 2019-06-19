package atsb.eve.model;

public class Structure {

	private long structId;
	private String structName;
	private int corpId;
	private int systemId;
	private int regionId;
	private int typeId;

	public Structure() {
	}

	public long getStructId() {
		return structId;
	}

	public void setStructId(long structId) {
		this.structId = structId;
	}

	public String getStructName() {
		return structName;
	}

	public void setStructName(String structName) {
		this.structName = structName;
	}

	public int getCorpId() {
		return corpId;
	}

	public void setCorpId(int corpId) {
		this.corpId = corpId;
	}

	public int getSystemId() {
		return systemId;
	}

	public void setSystemId(int systemId) {
		this.systemId = systemId;
	}

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
}
