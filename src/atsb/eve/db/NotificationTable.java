package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import atsb.eve.model.Notification;

public class NotificationTable {

	private static final String SELECT_BYALERT_SQL = "SELECT `notifId`,`time`,`userId`,`alertId`,`title`,`text`,`acknowledged` FROM dirtNotification WHERE `alertId`=?";
	private static final String INSERT_SQL = "INSERT INTO dirtNotification ("
			+ "`time`,`userId`,`alertId`,`title`,`text`,`acknowledged`) VALUES (?,?,?,?,?,?)";

	public static List<Notification> getNotificationsByAlert(Connection db, int alertId) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SELECT_BYALERT_SQL);
		stmt.setInt(1, alertId);
		ResultSet rs = stmt.executeQuery();
		List<Notification> notifs = new ArrayList<Notification>();
		while (rs.next()) {
			Notification n = new Notification();
			n.setNotifId(rs.getInt(1));
			n.setTime(rs.getTimestamp(2));
			n.setUserId(rs.getInt(3));
			n.setAlertId(rs.getInt(4));
			n.setTitle(rs.getString(5));
			n.setText(rs.getString(6));
			n.setAcknowledged(rs.getBoolean(7));
			notifs.add(n);
		}
		return notifs;
	}

	public static void insert(Connection db, Notification n) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(INSERT_SQL);
		stmt.setTimestamp(1, n.getTime());
		stmt.setInt(2, n.getUserId());
		stmt.setInt(3, n.getAlertId());
		stmt.setString(4, n.getTitle());
		stmt.setString(5, n.getText());
		stmt.setBoolean(6, n.isAcknowledged());
		stmt.execute();
	}

}
