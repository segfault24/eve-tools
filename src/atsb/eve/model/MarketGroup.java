package atsb.eve.model;

public class MarketGroup {

	private int marketGroupId;
	private int parentGroupId;
	private String marketGroupName;
	private String description;
	private boolean hasTypes;

	public MarketGroup() {
	}

	public int getMarketGroupId() {
		return marketGroupId;
	}

	public void setMarketGroupId(int marketGroupId) {
		this.marketGroupId = marketGroupId;
	}

	public int getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(int parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

	public String getMarketGroupName() {
		return marketGroupName;
	}

	public void setMarketGroupName(String marketGroupName) {
		this.marketGroupName = marketGroupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean hasTypes() {
		return hasTypes;
	}

	public void setHasTypes(boolean hasTypes) {
		this.hasTypes = hasTypes;
	}

}
