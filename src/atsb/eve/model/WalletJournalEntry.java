package atsb.eve.model;

import java.sql.Timestamp;

public class WalletJournalEntry {

	private long journalId;
	private int charId;
	private Timestamp date;
	private double amount;
	private double balance;
	private double tax;
	private int firstPartyId;
	private int secondPartyId;
	private int taxReceiverId;
	private String description;
	private String reason;
	private String refType;
	private long contextId;
	private String contextIdType;

	public WalletJournalEntry() {
	}

	public long getJournalId() {
		return journalId;
	}

	public void setJournalId(long journalId) {
		this.journalId = journalId;
	}

	public int getCharId() {
		return charId;
	}

	public void setCharId(int charId) {
		this.charId = charId;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public int getFirstPartyId() {
		return firstPartyId;
	}

	public void setFirstPartyId(int firstPartyId) {
		this.firstPartyId = firstPartyId;
	}

	public int getSecondPartyId() {
		return secondPartyId;
	}

	public void setSecondPartyId(int secondPartyId) {
		this.secondPartyId = secondPartyId;
	}

	public int getTaxReceiverId() {
		return taxReceiverId;
	}

	public void setTaxReceiverId(int taxReceiverId) {
		this.taxReceiverId = taxReceiverId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public long getContextId() {
		return contextId;
	}

	public void setContextId(long contextId) {
		this.contextId = contextId;
	}

	public String getContextIdType() {
		return contextIdType;
	}

	public void setContextIdType(String contextIdType) {
		this.contextIdType = contextIdType;
	}

}
