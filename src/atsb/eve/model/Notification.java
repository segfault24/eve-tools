package atsb.eve.model;

import java.sql.Timestamp;

public class Notification {

	private int notifId;
	private Timestamp time;
	private int userId;
	private Integer alertId;
	private Integer typeId;
	private String title;
	private String text;
	private boolean acknowledged;
	private boolean sent;

	public Notification() {
		acknowledged = false;
		sent = false;
	}

	public int getNotifId() {
		return notifId;
	}

	public void setNotifId(int notifId) {
		this.notifId = notifId;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAlertId() {
		return alertId;
	}

	public void setAlertId(int alertId) {
		this.alertId = alertId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isAcknowledged() {
		return acknowledged;
	}

	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

}
