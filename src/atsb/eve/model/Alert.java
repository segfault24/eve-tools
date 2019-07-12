package atsb.eve.model;

public class Alert {

	public enum AlertType {
		UNKNOWN(-1), PRICEVOLUME(1);
		protected final int value;

		AlertType(int i) {
			value = i;
		}

		public static AlertType fromValue(int v) {
			switch (v) {
			case 1:
				return PRICEVOLUME;
			case -1:
			default:
				return UNKNOWN;
			}
		}
	}

	private int alertId;
	private int userId;
	private AlertType alertType;
	private boolean enabled;
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	private String param5;

	public Alert() {
	}

	public int getAlertId() {
		return alertId;
	}

	public void setAlertId(int alertId) {
		this.alertId = alertId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public AlertType getAlertType() {
		return alertType;
	}

	public void setAlertType(AlertType alertType) {
		this.alertType = alertType;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getParam4() {
		return param4;
	}

	public void setParam4(String param4) {
		this.param4 = param4;
	}

	public String getParam5() {
		return param5;
	}

	public void setParam5(String param5) {
		this.param5 = param5;
	}

}
