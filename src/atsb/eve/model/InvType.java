package atsb.eve.model;

public class InvType {

	private int typeId;
	private int groupId;
	private String typeName;
	private String description;
	private double mass;
	private double volume;
	private double capacity;
	private int portionSize;
	private boolean published;
	private int marketGroupId;
	private int iconId;

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

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public int getPortionSize() {
		return portionSize;
	}

	public void setPortionSize(int portionSize) {
		this.portionSize = portionSize;
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

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

}
