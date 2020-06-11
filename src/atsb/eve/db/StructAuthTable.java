package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import atsb.eve.util.Utils;

public class StructAuthTable {

	public static List<Integer> getAuthKeyByStruct(Connection db, long structId) throws SQLException {
		PreparedStatement stmt;
		stmt = db.prepareStatement("SELECT keyId FROM dirtStructAuth WHERE structId=?");
		stmt.setLong(1, structId);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Integer> keys = new ArrayList<Integer>();
		while (rs.next()) {
			keys.add(rs.getInt(1));
		}
		Utils.closeQuietly(rs);
		Utils.closeQuietly(stmt);
		return keys;
	}

	public static List<Long> getStructIdByRegion(Connection db, int regionId) throws SQLException {
		PreparedStatement stmt = db.prepareStatement("SELECT DISTINCT d.structId FROM dirtStructAuth AS d "
				+ "JOIN structure AS s ON d.structId=s.structId WHERE s.regionId=?");
		stmt.setInt(1, regionId);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Long> structs = new ArrayList<Long>();
		while (rs.next()) {
			structs.add(rs.getLong(1));
		}
		Utils.closeQuietly(rs);
		Utils.closeQuietly(stmt);
		return structs;
	}

	public static void insert(Connection db, long structId, int keyId) throws SQLException {
		PreparedStatement stmt = db.prepareStatement("INSERT INTO dirtStructAuth (`structId`,`keyId`) VALUES (?,?)");
		stmt.setLong(1, structId);
		stmt.setInt(2, keyId);
		stmt.executeUpdate();
		Utils.closeQuietly(stmt);
	}

}
