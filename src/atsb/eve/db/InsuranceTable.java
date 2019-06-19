package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import atsb.eve.model.InsurancePrice;
import atsb.eve.util.Utils;

public class InsuranceTable {

	private static String DELETE_SQL = "TRUNCATE TABLE `insurancePrice`";
	private static String INSERT_SQL = "INSERT INTO insurancePrice (`typeId`,`name`,`cost`,`payout`) VALUES (?,?,?,?)";

	public static void insertMany(Connection db, Collection<InsurancePrice> prices) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(INSERT_SQL);
		for (InsurancePrice item : prices) {
			for (InsurancePrice.Level level : item.getLevels()) {
				stmt.setInt(1, item.getTypeId());
				stmt.setString(2, level.getName());
				stmt.setFloat(3, level.getCost());
				stmt.setFloat(4, level.getPayout());
				stmt.addBatch();
			}
		}
		stmt.executeBatch();
		Utils.closeQuietly(stmt);
	}

	public static void deleteAll(Connection db) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(DELETE_SQL);
		stmt.execute();
		Utils.closeQuietly(stmt);
	}

}
