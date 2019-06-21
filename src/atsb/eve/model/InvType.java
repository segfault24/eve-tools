package atsb.eve.model;

public class InvType {

	private int typeId;
	private int groupId;
	private String typeName;
	private String description;
	private double mass;
	private double volume;
	private boolean published;
	private int marketGroupId;

	public InvType() {
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public int getMarketGroupId() {
		return marketGroupId;
	}

	public void setMarketGroupId(int marketGroupId) {
		this.marketGroupId = marketGroupId;
	}

}
