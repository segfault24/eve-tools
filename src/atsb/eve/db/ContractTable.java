package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import atsb.eve.model.Contract;

public class ContractTable {

	private static final String SELECT_SQL = "SELECT `contractId`,`issuerId`,`issuerCorpId`,"
			+ "`assigneeId`,`acceptorId`,`availability`,`dateIssued`,`dateExpired`,`status`,`type`"
			+ " FROM contract WHERE contractId=?";

	private static final String INSERT_SQL = "INSERT INTO contract ("
			+ "`contractId`,`issuerId`,`issuerCorpId`,`assigneeId`,`acceptorId`,"
			+ "`availability`,`dateIssued`,`dateExpired`,`status`,`type`"
			+ ") VALUES (?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE "
			+ "`acceptorId`=VALUES(`acceptorId`), `status`=VALUES(`status`)";

	private static final int BATCH_SIZE = 1000;

	public static Contract selectById(Connection db, int contractId) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SELECT_SQL);
		stmt.setInt(1, contractId);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			Contract c = new Contract();
			c.setContractId(rs.getInt(1));
			c.setIssuerId(rs.getInt(2));
			c.setIssuerCorpId(rs.getInt(3));
			c.setAssigneeId(rs.getInt(4));
			c.setAcceptorId(rs.getInt(5));
			c.setAvailability(rs.getString(6));
			c.setDateIssued(rs.getTimestamp(7));
			c.setDateExpired(rs.getTimestamp(8));
			c.setStatus(rs.getString(9));
			c.setType(rs.getString(10));
			return c;
		} else {
			return null;
		}
	}

	public static void insertMany(Connection db, List<Contract> l) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(INSERT_SQL);
		int count = 0;
		for (Contract c : l) {
			stmt.setInt(1, c.getContractId());
			stmt.setInt(2, c.getIssuerId());
			stmt.setInt(3, c.getIssuerCorpId());
			stmt.setInt(4, c.getAssigneeId());
			stmt.setInt(5, c.getAcceptorId());
			stmt.setString(6, c.getAvailability());
			stmt.setTimestamp(7, c.getDateIssued());
			stmt.setTimestamp(8, c.getDateExpired());
			stmt.setString(9, c.getStatus());
			stmt.setString(10, c.getType());
			stmt.addBatch();
			count++;
			if (count % BATCH_SIZE == 0 || count == l.size()) {
				stmt.executeBatch();
			}
		}
	}

}
