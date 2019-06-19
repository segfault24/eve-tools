package atsb.eve.model;

import java.sql.Timestamp;

public class Contract {

	private int contractId;
	private int issuerId;
	private int issuerCorpId;
	private int assigneeId;
	private int acceptorId;
	private String availability;
	private Timestamp dateIssued;
	private Timestamp dateExpired;
	private String status;
	private String type;

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

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
