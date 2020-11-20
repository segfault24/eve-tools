package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import atsb.eve.model.Alert;
import atsb.eve.model.Alert.AlertType;
import atsb.eve.util.Utils;

public class AlertTable {

	private static final String SELECT_ALL_SQL = "SELECT `alertId`,`userId`,`alertType`,`enabled`,`param1`,`param2`,`param3`,`param4`,`param5` FROM dirtalert";
	private static final String SELECT_BYUSER_SQL = "SELECT `alertId`,`userId`,`alertType`,`enabled`,`param1`,`param2`,`param3`,`param4`,`param5` FROM dirtalert WHERE `userId`=?";

	public static List<Alert> getAllAlerts(Connection db) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SELECT_ALL_SQL);
		ResultSet rs = stmt.executeQuery();
		List<Alert> alerts = new ArrayList<Alert>();
		while (rs.next()) {
			Alert a = new Alert();
			a.setAlertId(rs.getInt(1));
			a.setUserId(rs.getInt(2));
			a.setAlertType(AlertType.fromValue(rs.getInt(3)));
			a.setEnabled(rs.getBoolean(4));
			a.setParam1(rs.getString(5));
			a.setParam2(rs.getString(6));
			a.setParam3(rs.getString(7));
			a.setParam4(rs.getString(8));
			a.setParam5(rs.getString(9));
			alerts.add(a);
		}
		Utils.closeQuietly(rs);
		Utils.closeQuietly(stmt);
		return alerts;
	}

	public static List<Alert> getAlertsByUser(Connection db, int userId) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SELECT_BYUSER_SQL);
		stmt.setInt(1, userId);
		ResultSet rs = stmt.executeQuery();
		List<Alert> alerts = new ArrayList<Alert>();
		while (rs.next()) {
			Alert a = new Alert();
			a.setAlertId(rs.getInt(1));
			a.setUserId(rs.getInt(2));
			a.setAlertType(AlertType.fromValue(rs.getInt(3)));
			a.setEnabled(rs.getBoolean(4));
			a.setParam1(rs.getString(5));
			a.setParam2(rs.getString(6));
			a.setParam3(rs.getString(7));
			a.setParam4(rs.getString(8));
			a.setParam5(rs.getString(9));
			alerts.add(a);
		}
		Utils.closeQuietly(rs);
		Utils.closeQuietly(stmt);
		return alerts;
	}

}
