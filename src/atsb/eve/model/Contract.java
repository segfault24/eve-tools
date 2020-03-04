package atsb.eve.model;

import java.sql.Timestamp;
import java.util.HashMap;

public class Contract {

	private int contractId;
	private int issuerId;
	private int issuerCorpId;
	private int assigneeId;
	private int acceptorId;
	private ContractAvailability availability;
	private ContractStatus status;
	private ContractType type;
	private Timestamp dateIssued;
	private Timestamp dateExpired;
	private Timestamp dateAccepted;
	private Timestamp dateCompleted;
	private String title;
	private boolean forCorp;
	private long startLocationId;
	private long endLocationId;
	private int daysToComplete;
	private double price;
	private double reward;
	private double collateral;
	private double buyout;
	private double volume;

	public Contract() {
	}

	public int getContractId() {
		return contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}

	public int getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(int issuerId) {
		this.issuerId = issuerId;
	}

	public int getIssuerCorpId() {
		return issuerCorpId;
	}

	public void setIssuerCorpId(int issuerCorpId) {
		this.issuerCorpId = issuerCorpId;
	}

	public int getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(int assigneeId) {
		this.assigneeId = assigneeId;
	}

	public int getAcceptorId() {
		return acceptorId;
	}

	public void setAcceptorId(int acceptorId) {
		this.acceptorId = acceptorId;
	}

	public ContractAvailability getAvailability() {
		return availability;
	}

	public void setAvailability(ContractAvailability availability) {
		this.availability = availability;
	}

	public ContractStatus getStatus() {
		return status;
	}

	public void setStatus(ContractStatus status) {
		this.status = status;
	}

	public ContractType getType() {
		return type;
	}

	public void setType(ContractType type) {
		this.type = type;
	}

	public Timestamp getDateIssued() {
		return dateIssued;
	}

	public void setDateIssued(Timestamp dateIssued) {
		this.dateIssued = dateIssued;
	}

	public Timestamp getDateExpired() {
		return dateExpired;
	}

	public void setDateExpired(Timestamp dateExpired) {
		this.dateExpired = dateExpired;
	}

	public Timestamp getDateAccepted() {
		return dateAccepted;
	}

	public void setDateAccepted(Timestamp dateAccepted) {
		this.dateAccepted = dateAccepted;
	}

	public Timestamp getDateCompleted() {
		return dateCompleted;
	}

	public void setDateCompleted(Timestamp dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isForCorp() {
		return forCorp;
	}

	public void setForCorp(boolean forCorp) {
		this.forCorp = forCorp;
	}

	public long getStartLocationId() {
		return startLocationId;
	}

	public void setStartLocationId(long startLocationId) {
		this.startLocationId = startLocationId;
	}

	public long getEndLocationId() {
		return endLocationId;
	}

	public void setEndLocationId(long endLocationId) {
		this.endLocationId = endLocationId;
	}

	public int getDaysToComplete() {
		return daysToComplete;
	}

	public void setDaysToComplete(int daysToComplete) {
		this.daysToComplete = daysToComplete;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getReward() {
		return reward;
	}

	public void setReward(double reward) {
		this.reward = reward;
	}

	public double getCollateral() {
		return collateral;
	}

	public void setCollateral(double collateral) {
		this.collateral = collateral;
	}

	public double getBuyout() {
		return buyout;
	}

	public void setBuyout(double buyout) {
		this.buyout = buyout;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public enum ContractStatus {
		OUTSTANDING(1), IN_PROGRESS(2), FINISHED_ISSUER(3), FINISHED_CONTRACTOR(4), FINISHED(5), CANCELLED(6),
		REJECTED(7), FAILED(8), DELETED(9), REVERSED(10), UNKNOWN(11);

		private int value;
		private static HashMap<Integer, ContractStatus> map = new HashMap<Integer, ContractStatus>();

		static {
			for (ContractStatus s : ContractStatus.values()) {
				map.put(s.value, s);
			}
		}

		private ContractStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static ContractStatus valueOf(int key) {
			return (ContractStatus) map.get(key);
		}
	}

	public enum ContractType {
		UNKNOWN(1), ITEM_EXCHANGE(2), AUCTION(3), COURIER(4), LOAN(5);

		private int value;
		private static HashMap<Integer, ContractType> map = new HashMap<Integer, ContractType>();

		static {
			for (ContractType s : ContractType.values()) {
				map.put(s.value, s);
			}
		}

		private ContractType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static ContractType valueOf(int key) {
			return (ContractType) map.get(key);
		}
	}

	public enum ContractAvailability {
		PUBLIC(1), PERSONAL(2), CORPORATION(3), ALLIANCE(4), UNKNOWN(5);

		private int value;
		private static HashMap<Integer, ContractAvailability> map = new HashMap<Integer, ContractAvailability>();

		static {
			for (ContractAvailability s : ContractAvailability.values()) {
				map.put(s.value, s);
			}
		}

		private ContractAvailability(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static ContractAvailability valueOf(int key) {
			return (ContractAvailability) map.get(key);
		}
	}

}
