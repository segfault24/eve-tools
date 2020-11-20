package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import atsb.eve.model.Constellation;
import atsb.eve.model.Region;
import atsb.eve.model.SolarSystem;
import atsb.eve.model.Station;
import atsb.eve.util.Utils;

public class MapTables {

	private static final String REGION_UPSERT_SQL = "INSERT INTO region (`regionId`,`regionName`,`description`) "
			+ "VALUES (?,?,?) ON DUPLICATE KEY UPDATE `regionName`=VALUES(`regionName`), `description`=VALUES(`description`)";
	private static final String CONSTELLATION_UPSERT_SQL = "INSERT INTO constellation (`constellationId`,`constellationName`,`regionId`) "
			+ "VALUES (?,?,?) ON DUPLICATE KEY UPDATE `constellationName`=VALUES(`constellationName`), `regionId`=VALUES(`regionId`)";
	private static final String SOLARSYSTEM_UPSERT_SQL = "INSERT INTO solarsystem "
			+ "(`solarSystemId`, `solarSystemName`, `constellationId`, `regionId`, `x`, `y`, `z`, `security`) "
			+ "VALUES (?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE "
			+ "`solarSystemName`=VALUES(`solarSystemName`), `constellationId`=VALUES(`constellationId`), `regionId`=VALUES(`regionId`), "
			+ "`x`=VALUES(`x`), `y`=VALUES(`y`), `z`=VALUES(`z`), `security`=VALUES(`security`)";
	private static final String STATION_UPSERT_SQL = "INSERT INTO station (`stationId`,`stationName`,`solarSystemId`,`constellationId`,`regionId`) "
			+ "VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE "
			+ "`stationName`=VALUES(`stationName`), `solarSystemId`=VALUES(`solarSystemId`), `constellationId`=VALUES(`constellationId`), `regionId`=VALUES(`regionId`)";

	private static final String REGION_BY_SYSTEM_SQL = "SELECT r.`regionId`, r.`regionName`, r.`description` FROM region AS r JOIN solarsystem AS s ON s.regionId=r.regionId WHERE s.solarSystemId=?";
	private static final String REGION_BY_NAME_SQL = "SELECT `regionId`, `regionName`, `description` FROM region WHERE `regionName`=?";

	public static void upsert(Connection db, Region r) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(REGION_UPSERT_SQL);
		stmt.setInt(1, r.getReigonId());
		stmt.setString(2, r.getRegionName());
		stmt.setString(3, r.getDescription());
		stmt.execute();
		Utils.closeQuietly(stmt);
	}

	public static void delete(Connection db, Region r) throws SQLException {
		PreparedStatement stmt = db.prepareStatement("DELETE FROM region WHERE regionId=?");
		stmt.setInt(1, r.getReigonId());
		stmt.execute();
		Utils.closeQuietly(stmt);
	}

	public static void upsert(Connection db, Constellation c) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(CONSTELLATION_UPSERT_SQL);
		stmt.setInt(1, c.getConstellationId());
		stmt.setString(2, c.getConstellationName());
		stmt.setInt(3, c.getRegionId());
		stmt.execute();
		Utils.closeQuietly(stmt);
	}

	public static void delete(Connection db, Constellation c) throws SQLException {
		PreparedStatement stmt = db.prepareStatement("DELETE FROM constellation WHERE constellationId=?");
		stmt.setInt(1, c.getConstellationId());
		stmt.execute();
		Utils.closeQuietly(stmt);
	}

	public static void upsert(Connection db, SolarSystem s) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SOLARSYSTEM_UPSERT_SQL);
		stmt.setInt(1, s.getSolarSystemId());
		stmt.setString(2, s.getSolarSystemName());
		stmt.setInt(3, s.getConstellationId());
		stmt.setInt(4, s.getRegionId());
		stmt.setDouble(5, s.getX());
		stmt.setDouble(6, s.getY());
		stmt.setDouble(7, s.getZ());
		stmt.setDouble(8, s.getSecurity());
		stmt.execute();
		Utils.closeQuietly(stmt);
	}

	public static void delete(Connection db, SolarSystem s) throws SQLException {
		PreparedStatement stmt = db.prepareStatement("DELETE FROM solarsystem WHERE solarSystemId=?");
		stmt.setInt(1, s.getSolarSystemId());
		stmt.execute();
		Utils.closeQuietly(stmt);
	}

	public static void upsert(Connection db, Station s) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(STATION_UPSERT_SQL);
		stmt.setInt(1, s.getStationId());
		stmt.setString(2, s.getStationName());
		stmt.setInt(3, s.getSolarSystemId());
		stmt.setInt(4, s.getConstellationId());
		stmt.setInt(5, s.getRegionId());
		stmt.execute();
		Utils.closeQuietly(stmt);
	}

	public static void delete(Connection db, Station s) throws SQLException {
		PreparedStatement stmt = db.prepareStatement("DELETE FROM station WHERE stationId=?");
		stmt.setInt(1, s.getStationId());
		stmt.execute();
		Utils.closeQuietly(stmt);
	}

	public static Region findRegionBySystem(Connection db, int systemId) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(REGION_BY_SYSTEM_SQL);
		stmt.setInt(1, systemId);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		Region r = new Region();
		r.setReigonId(rs.getInt(1));
		r.setRegionName(rs.getString(2));
		r.setDescription(rs.getString(3));
		Utils.closeQuietly(stmt);
		return r;
	}

	public static Region findRegionByName(Connection db, String name) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(REGION_BY_NAME_SQL);
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		Region r = new Region();
		r.setReigonId(rs.getInt(1));
		r.setRegionName(rs.getString(2));
		r.setDescription(rs.getString(3));
		Utils.closeQuietly(stmt);
		return r;
	}

}
