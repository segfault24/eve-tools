package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import atsb.eve.model.Contract;
import atsb.eve.model.Contract.ContractAvailability;
import atsb.eve.model.Contract.ContractStatus;
import atsb.eve.model.Contract.ContractType;
import atsb.eve.util.Utils;

public class CorpContractTable {

	private static final String SELECT_BY_ID_SQL = "SELECT `contractId`,`issuerId`,`issuerCorpId`,"
			+ "`assigneeId`,`acceptorId`,`availability`,`status`,`type`,`dateIssued`,`dateExpired`,"
			+ "`dateAccepted`,`dateCompleted`,`title`,`forCorp`,`startLocationId`,`endLocationId`,"
			+ "`daysToComplete`,`price`,`reward`,`collateral`,`buyout`,`volume`"
			+ " FROM corpcontract WHERE contractId=?";

	private static final String SELECT_OUTSTANDING_EXCHANGE_SQL = "SELECT `contractId`,`issuerId`,`issuerCorpId`,"
			+ "`assigneeId`,`acceptorId`,`availability`,`status`,`type`,`dateIssued`,`dateExpired`,"
			+ "`dateAccepted`,`dateCompleted`,`title`,`forCorp`,`startLocationId`,`endLocationId`,"
			+ "`daysToComplete`,`price`,`reward`,`collateral`,`buyout`,`volume`" + " FROM corpcontract WHERE status="
			+ ContractStatus.OUTSTANDING.getValue() + " AND type=" + ContractType.ITEM_EXCHANGE.getValue()
			+ " AND dateExpired>NOW()";

	private static final String INSERT_SQL = "INSERT INTO corpcontract ("
			+ "`contractId`,`issuerId`,`issuerCorpId`,`assigneeId`,`acceptorId`,`availability`,"
			+ "`status`,`type`,`dateIssued`,`dateExpired`,`dateAccepted`,`dateCompleted`,"
			+ "`title`,`forCorp`,`startLocationId`,`endLocationId`,`daysToComplete`,"
			+ "`price`,`reward`,`collateral`,`buyout`,`volume`"
			+ ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE "
			+ "`acceptorId`=VALUES(`acceptorId`), `status`=VALUES(`status`),"
			+ "`dateAccepted`=VALUES(`dateAccepted`), `dateCompleted`=VALUES(`dateCompleted`)";

	private static final int BATCH_SIZE = 1000;

	public static Contract selectById(Connection db, int contractId) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SELECT_BY_ID_SQL);
		stmt.setInt(1, contractId);
		ResultSet rs = stmt.executeQuery();
		Contract c = null;
		if (rs.next()) {
			c = new Contract();
			c.setContractId(rs.getInt(1));
			c.setIssuerId(rs.getInt(2));
			c.setIssuerCorpId(rs.getInt(3));
			c.setAssigneeId(rs.getInt(4));
			c.setAcceptorId(rs.getInt(5));
			c.setAvailability(ContractAvailability.valueOf(rs.getInt(6)));
			c.setStatus(ContractStatus.valueOf(rs.getInt(7)));
			c.setType(ContractType.valueOf(rs.getInt(8)));
			c.setDateIssued(rs.getTimestamp(9));
			c.setDateExpired(rs.getTimestamp(10));
			c.setDateAccepted(rs.getTimestamp(11));
			c.setDateCompleted(rs.getTimestamp(12));
			c.setTitle(rs.getString(13));
			c.setForCorp(rs.getBoolean(14));
			c.setStartLocationId(rs.getLong(15));
			c.setEndLocationId(rs.getLong(16));
			c.setDaysToComplete(rs.getInt(17));
			c.setPrice(rs.getDouble(18));
			c.setReward(rs.getDouble(19));
			c.setCollateral(rs.getDouble(20));
			c.setBuyout(rs.getDouble(21));
			c.setVolume(rs.getDouble(22));
		}
		Utils.closeQuietly(rs);
		Utils.closeQuietly(stmt);
		return c;
	}

	public static void upsertMany(Connection db, List<Contract> l) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(INSERT_SQL);
		int count = 0;
		for (Contract c : l) {
			stmt.setInt(1, c.getContractId());
			stmt.setInt(2, c.getIssuerId());
			stmt.setInt(3, c.getIssuerCorpId());
			stmt.setInt(4, c.getAssigneeId());
			stmt.setInt(5, c.getAcceptorId());
			stmt.setInt(6, c.getAvailability().getValue());
			stmt.setInt(7, c.getStatus().getValue());
			stmt.setInt(8, c.getType().getValue());
			stmt.setTimestamp(9, c.getDateIssued());
			stmt.setTimestamp(10, c.getDateExpired());
			stmt.setTimestamp(11, c.getDateAccepted());
			stmt.setTimestamp(12, c.getDateCompleted());
			stmt.setString(13, c.getTitle());
			stmt.setBoolean(14, c.isForCorp());
			stmt.setLong(15, c.getStartLocationId());
			stmt.setLong(16, c.getEndLocationId());
			stmt.setInt(17, c.getDaysToComplete());
			stmt.setDouble(18, c.getPrice());
			stmt.setDouble(19, c.getReward());
			stmt.setDouble(20, c.getCollateral());
			stmt.setDouble(21, c.getBuyout());
			stmt.setDouble(22, c.getVolume());
			stmt.addBatch();
			count++;
			if (count % BATCH_SIZE == 0 || count == l.size()) {
				stmt.executeBatch();
			}
		}
		Utils.closeQuietly(stmt);
	}

	public static List<Contract> selectOutstandingExchange(Connection db) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SELECT_OUTSTANDING_EXCHANGE_SQL);
		ResultSet rs = stmt.executeQuery();
		List<Contract> contracts = new ArrayList<Contract>();
		while (rs.next()) {
			Contract c = new Contract();
			c.setContractId(rs.getInt(1));
			c.setIssuerId(rs.getInt(2));
			c.setIssuerCorpId(rs.getInt(3));
			c.setAssigneeId(rs.getInt(4));
			c.setAcceptorId(rs.getInt(5));
			c.setAvailability(ContractAvailability.valueOf(rs.getInt(6)));
			c.setStatus(ContractStatus.valueOf(rs.getInt(7)));
			c.setType(ContractType.valueOf(rs.getInt(8)));
			c.setDateIssued(rs.getTimestamp(9));
			c.setDateExpired(rs.getTimestamp(10));
			c.setDateAccepted(rs.getTimestamp(11));
			c.setDateCompleted(rs.getTimestamp(12));
			c.setTitle(rs.getString(13));
			c.setForCorp(rs.getBoolean(14));
			c.setStartLocationId(rs.getLong(15));
			c.setEndLocationId(rs.getLong(16));
			c.setDaysToComplete(rs.getInt(17));
			c.setPrice(rs.getDouble(18));
			c.setReward(rs.getDouble(19));
			c.setCollateral(rs.getDouble(20));
			c.setBuyout(rs.getDouble(21));
			c.setVolume(rs.getDouble(22));
			contracts.add(c);
		}
		Utils.closeQuietly(stmt);
		return contracts;
	}

}
