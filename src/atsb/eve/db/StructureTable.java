package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import atsb.eve.model.Structure;
import atsb.eve.util.Utils;

public class StructureTable {

	private static final String INSERT_SQL = "INSERT INTO structure (" + "`structId`,`structName`,`corpId`,`systemId`,`regionId`,`typeId`"
			+ ") VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE " + "`structName`=VALUES(`structName`)," + "`corpId`=VALUES(`corpId`),"
			+ "`systemId`=VALUES(`systemId`)," + "`regionId`=VALUES(`regionId`)," + "`typeId`=VALUES(`typeId`)";
	private static final String SELECT_SQL = "SELECT `structId`,`structName`,`corpId`,`systemId`,`regionId`,`typeId` FROM structure WHERE structId=?";

	public static void insert(Connection db, Structure s) throws SQLException {
		PreparedStatement stmt;
		stmt = db.prepareStatement(INSERT_SQL);
		stmt.setLong(1, s.getStructId());
		stmt.setString(2, s.getStructName());
		stmt.setInt(3, s.getCorpId());
		stmt.setInt(4, s.getSystemId());
		stmt.setInt(5, s.getRegionId());
		stmt.setInt(6, s.getTypeId());
		stmt.execute();
		Utils.closeQuietly(stmt);
	}

	public static Structure find(Connection db, long structId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Structure s = null;
		try {
			stmt = db.prepareStatement(SELECT_SQL);
			stmt.setLong(1, structId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				s = new Structure();
				s.setStructId(structId);
				s.setStructName(rs.getString(2));
				s.setCorpId(rs.getInt(3));
				s.setSystemId(rs.getInt(4));
				s.setRegionId(rs.getInt(5));
				s.setTypeId(rs.getInt(6));
			}
		} catch (SQLException e) {
			s = null;
		} finally {
			Utils.closeQuietly(rs);
			Utils.closeQuietly(stmt);
		}
		return s;
	}

}
