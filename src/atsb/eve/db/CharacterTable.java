package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import atsb.eve.model.Character;

public class CharacterTable {

	private static final String UPSERT_SQL = "INSERT INTO `character` (`charId`,`charName`,`corpId`,`allianceId`,`birthday`) "
			+ "VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE `corpId`=VALUES(`corpId`), `allianceId`=VALUES(`allianceId`)";
	private static final String SELECT_IDS_SQL = "SELECT `charId` FROM `character`";

	public static void upsert(Connection db, Character c) throws SQLException {
		PreparedStatement stmt;
		stmt = db.prepareStatement(UPSERT_SQL);
		stmt.setInt(1, c.getCharId());
		stmt.setString(2, c.getCharName());
		stmt.setInt(3, c.getCorpId());
		stmt.setInt(4, c.getAllianceId());
		stmt.setDate(5, c.getBirthday());
		stmt.execute();
	}

	public static List<Integer> getAllIds(Connection db) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SELECT_IDS_SQL);
		ResultSet rs = stmt.executeQuery();
		List<Integer> ids = new ArrayList<Integer>();
		while (rs.next()) {
			ids.add(rs.getInt(1));
		}
		return ids;
	}

}
