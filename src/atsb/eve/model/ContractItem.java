package atsb.eve.model;

public class ContractItem {

	private int contractItemId;
	private int contractId;
	private int typeId;
	private int quantity;
	private long recordId;
	private boolean included;
	private boolean singleton;

	public ContractItem() {
	}

	public int getContractItemId() {
		return contractItemId;
	}

	public void setContractItemId(int contractItemId) {
		this.contractItemId = contractItemId;
	}

	public int getContractId() {
		return contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public long getRecordId() {
		return recordId;
	}

	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	public boolean isIncluded() {
		return included;
	}

	public void setIncluded(boolean included) {
		this.included = included;
	}

	public boolean isSingleton() {
		return singleton;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

}
