package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import atsb.eve.model.Corporation;
import atsb.eve.util.Utils;

public class CorporationTable {

	private static final String INSERT_SQL = "INSERT INTO corporation (`corpId`,`corpName`,`ticker`,`allianceId`,`ceo`,`creator`,`creationDate`,`memberCount`,`taxRate`,`url`)"
			+ " VALUES (?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE `allianceId`=VALUES(`allianceId`),`ceo`=VALUES(`ceo`),`memberCount`=VALUES(`memberCount`),`taxRate`=VALUES(`taxRate`),`url`=VALUES(`url`)";
	private static final String SELECT_IDS_SQL = "SELECT `corpId` FROM corporation";

	public static void upsert(Connection db, Corporation s) throws SQLException {
		PreparedStatement stmt;
		stmt = db.prepareStatement(INSERT_SQL);
		stmt.setInt(1, s.getCorpId());
		stmt.setString(2, s.getCorpName());
		stmt.setString(3, s.getTicker());
		stmt.setInt(4, s.getAllianceId());
		stmt.setInt(5, s.getCeoId());
		stmt.setInt(6, s.getCreatorId());
		stmt.setDate(7, s.getCreationDate());
		stmt.setInt(8, s.getMemberCount());
		stmt.setDouble(9, s.getTaxRate());
		stmt.setString(10, s.getUrl());
		stmt.execute();
		Utils.closeQuietly(stmt);
	}

	public static List<Integer> getAllIds(Connection db) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SELECT_IDS_SQL);
		ResultSet rs = stmt.executeQuery();
		List<Integer> ids = new ArrayList<Integer>();
		while (rs.next()) {
			ids.add(rs.getInt(1));
		}
		Utils.closeQuietly(rs);
		Utils.closeQuietly(stmt);
		return ids;
	}

}
