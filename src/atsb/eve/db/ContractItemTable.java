package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import atsb.eve.model.ContractItem;
import atsb.eve.util.Utils;

public class ContractItemTable {

	private static final String DELETE_SQL = "DELETE FROM contractitem WHERE contractId=?";

	private static final String SELECT_SQL = "SELECT `contractId`,`typeId`,`quantity`,"
			+ "`recordId`,`included`,`singleton` FROM contractitem WHERE contractId=?";

	private static final String INSERT_SQL = "INSERT INTO contractitem ("
			+ "`contractId`,`typeId`,`quantity`,`recordId`,`included`,`singleton`"
			+ ") VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE `quantity`=VALUES(`quantity`)";

	public static void deleteByContractId(Connection db, int contractId) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(DELETE_SQL);
		stmt.setInt(1, contractId);
		stmt.execute();
		Utils.closeQuietly(stmt);
	}

	public static  List<ContractItem> selectByContractId(Connection db, int contractId) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SELECT_SQL);
		stmt.setInt(1, contractId);
		ResultSet rs = stmt.executeQuery();
		List<ContractItem> items = new ArrayList<ContractItem>();
		while (rs.next()) {
			ContractItem i = new ContractItem();
			i.setContractId(rs.getInt(1));
			i.setTypeId(rs.getInt(2));
			i.setQuantity(rs.getInt(3));
			i.setRecordId(rs.getLong(4));
			i.setIncluded(rs.getBoolean(5));
			i.setSingleton(rs.getBoolean(6));
			items.add(i);
		}
		Utils.closeQuietly(rs);
		Utils.closeQuietly(stmt);
		return items;
	}

	public static void insertMany(Connection db, List<ContractItem> items) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(INSERT_SQL);
		for (ContractItem i : items) {
			stmt.setInt(1, i.getContractId());
			stmt.setInt(2, i.getTypeId());
			stmt.setInt(3, i.getQuantity());
			stmt.setLong(4, i.getRecordId());
			stmt.setBoolean(5, i.isIncluded());
			stmt.setBoolean(6, i.isSingleton());
			stmt.addBatch();
		}
		stmt.executeBatch();
		Utils.closeQuietly(stmt);
	}

}
