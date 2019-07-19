package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import atsb.eve.model.InvType;

public class InvTypeTable {

	private static final String SELECT_ALL_IDS_SQL = "SELECT typeId FROM invType";
	private static final String SELECT_BY_ID_SQL = "SELECT `typeId`,`groupId`,`typeName`,"
			+ "`description`,`mass`,`volume`,`published`,`marketGroupId` FROM invType WHERE `typeId`=?";
	private static final String UPSERT_SQL = "INSERT INTO invType "
			+ "(`typeId`,`groupId`,`typeName`,`description`,`mass`,`volume`,`published`,`marketGroupId`) "
			+ "VALUES (?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE "
			+ "`typeId`=VALUES(`typeId`), `groupId`=VALUES(`groupId`), `typeName`=VALUES(`typeName`), `description`=VALUES(`description`), "
			+ "`mass`=VALUES(`mass`), `volume`=VALUES(`volume`), `published`=VALUES(`published`), `marketGroupId`=VALUES(`marketGroupId`)";

	private static final String SELECT_MARKETABLE_TYPES_SQL = "SELECT typeId FROM invType WHERE published=1 AND marketGroupId IS NOT NULL";

	public static List<Integer> selectAllIds(Connection db) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SELECT_ALL_IDS_SQL);
		ResultSet rs = stmt.executeQuery();
		List<Integer> types = new ArrayList<Integer>();
		while (rs.next()) {
			types.add(rs.getInt(1));
		}
		return types;
	}

	public static InvType getById(Connection db, int i) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SELECT_BY_ID_SQL);
		stmt.setInt(1, i);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			InvType t = new InvType();
			t.setTypeId(rs.getInt(1));
			t.setGroupId(rs.getInt(2));
			t.setTypeName(rs.getString(3));
			t.setDescription(rs.getString(4));
			t.setMass(rs.getDouble(5));
			t.setVolume(rs.getDouble(6));
			t.setPublished(rs.getBoolean(7));
			t.setMarketGroupId(rs.getInt(8));
			return t;
		} else {
			return null;
		}
	}

	public static void upsert(Connection db, InvType i) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(UPSERT_SQL);
		stmt.setInt(1, i.getTypeId());
		stmt.setInt(2, i.getGroupId());
		stmt.setString(3, i.getTypeName());
		stmt.setString(4, i.getDescription());
		stmt.setDouble(5, i.getMass());
		stmt.setDouble(6, i.getVolume());
		stmt.setBoolean(7, i.isPublished());
		stmt.setInt(8, i.getMarketGroupId());
		stmt.execute();
	}

	public static void delete(Connection db, InvType i) throws SQLException {
		PreparedStatement stmt = db.prepareStatement("DELETE FROM invType WHERE typeId=?");
		stmt.setInt(1, i.getTypeId());
		stmt.execute();
	}

	public static List<Integer> getMarketableTypeIds(Connection db) throws SQLException {
		List<Integer> typeIds = new ArrayList<Integer>();
		PreparedStatement stmt = db.prepareStatement(SELECT_MARKETABLE_TYPES_SQL);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			typeIds.add(rs.getInt("typeId"));
		}
		return typeIds;
	}

}
