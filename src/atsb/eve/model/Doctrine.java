package atsb.eve.model;

public class Doctrine {

	private int doctrineId;
	private int listId;
	private long locationId;
	private int quantity;
	private int target;
	private double lowestPrice;

	public int getDoctrineId() {
		return doctrineId;
	}

	public void setDoctrineId(int doctrineId) {
		this.doctrineId = doctrineId;
	}

	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void incrementQuantity() {
		quantity++;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

}
