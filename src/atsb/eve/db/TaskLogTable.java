package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import atsb.eve.model.TaskLog;
import atsb.eve.util.Utils;

public class TaskLogTable {

	private static final String SELECT_LATEST_TASK_SQL = "SELECT `taskLogId`,`taskName`,`startTime`,`finishTime`,`duration`,`success`,`error` FROM tasklog WHERE `taskName`=? ORDER BY `finishTime` DESC LIMIT 1";
	private static final String INSERT_TASK_SQL = "INSERT INTO tasklog (`taskName`,`startTime`,`finishTime`,`duration`,`success`,`error`) VALUES(?,?,?,?,?,?)";

	/**
	 * Get the latest TaskLog for the given taskName
	 * 
	 * @param db
	 * @param taskName
	 * @return
	 */
	public static TaskLog getLatestTaskLog(Connection db, String taskName) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		TaskLog t = null;
		try {
			stmt = db.prepareStatement(SELECT_LATEST_TASK_SQL);
			stmt.setString(1, taskName);
			rs = stmt.executeQuery();
			if (rs.next()) {
				t = new TaskLog();
				t.setTaskLogId(rs.getInt(1));
				t.setTaskName(taskName);
				t.setStartTime(rs.getTimestamp(3));
				t.setFinishTime(rs.getTimestamp(4));
				t.setDuration(rs.getInt(5));
				t.setSuccess(rs.getBoolean(6));
				t.setError(rs.getString(7));
			}
		} catch (SQLException e) {
			t = null;
		} finally {
			Utils.closeQuietly(rs);
			Utils.closeQuietly(stmt);
		}
		return t;
	}

	/**
	 * Inserts the given TaskLog
	 * 
	 * @param db
	 * @param taskLog
	 * @throws SQLException
	 */
	public static void insertTaskLog(Connection db, TaskLog taskLog) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(INSERT_TASK_SQL);
		stmt.setString(1, taskLog.getTaskName());
		stmt.setTimestamp(2, taskLog.getStartTime());
		stmt.setTimestamp(3, taskLog.getFinishTime());
		stmt.setInt(4, taskLog.getDuration());
		stmt.setBoolean(5, taskLog.isSuccess());
		stmt.setString(6, taskLog.getError());
		stmt.execute();
		Utils.closeQuietly(stmt);
	}

}
