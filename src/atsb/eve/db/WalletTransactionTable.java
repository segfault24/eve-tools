package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import atsb.eve.model.WalletTransaction;
import atsb.eve.util.Utils;

public class WalletTransactionTable {

	private static final String INSERT_IGNORE_SQL = "INSERT IGNORE INTO `wallettransaction` (`transactionId`,`charId`,`clientId`,`date`,`isBuy`,`isPersonal`,`typeId`,`quantity`,`unitPrice`,`locationId`,`journalRefId`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	private static final String LATEST_SELECT_SQL = "SELECT `transactionId` FROM `wallettransaction` WHERE `charId`=? ORDER BY `date` DESC LIMIT 1;";

	public static long getLatestTransactionId(Connection db, int charId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		long retval = 0;
		try {
			stmt = db.prepareStatement(LATEST_SELECT_SQL);
			stmt.setInt(1, charId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				retval = rs.getLong(1);
			}
		} catch (SQLException e) {
			retval = 0;
		} finally {
			Utils.closeQuietly(rs);
			Utils.closeQuietly(stmt);
		}
		return retval;
	}

	public static boolean insertMany(Connection db, List<WalletTransaction> ts) {
		boolean result = true;
		PreparedStatement stmt = null;
		try {
			stmt = db.prepareStatement(INSERT_IGNORE_SQL);
			for (WalletTransaction t : ts) {
				stmt.setLong(1, t.getTransactionId());
				stmt.setInt(2, t.getCharId());
				stmt.setInt(3, t.getClientId());
				stmt.setTimestamp(4, t.getDate());
				stmt.setBoolean(5,  t.isBuy());
				stmt.setBoolean(6, t.isPersonal());
				stmt.setInt(7, t.getTypeId());
				stmt.setInt(8, t.getQuantity());
				stmt.setDouble(9, t.getUnitPrice());
				stmt.setLong(10, t.getLocationId());
				stmt.setLong(11, t.getJournalRefId());
				stmt.addBatch();
			}
			int[] rs = stmt.executeBatch();

			// if any of the inserts returned non-one, it was probably a duplicate
			for (int r : rs) {
				if (r != 1) {
					result = false;
				}
			}
		} catch (SQLException e) {
			result = false;
		} finally {
			Utils.closeQuietly(stmt);
		}
		return result;
	}

}
