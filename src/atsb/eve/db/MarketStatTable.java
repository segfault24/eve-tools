package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import atsb.eve.model.MarketStat;
import atsb.eve.util.Utils;

public class MarketStatTable {

	private static final String UPSERT_SQL = "INSERT INTO marketstat (`regionId`,`typeId`,`ma30`,`ma90`) "
			+ "VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE "
			+ "`ma30`=VALUES(`ma30`), `ma90`=VALUES(`ma90`)";

	private static final int BATCH_SIZE = 1000;

	public static void upsert(Connection db, MarketStat s) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(UPSERT_SQL);
		stmt.setInt(1, s.getRegionId());
		stmt.setInt(2, s.getTypeId());
		stmt.setLong(3, s.getMa30());
		stmt.setLong(4, s.getMa90());
		stmt.execute();
		Utils.closeQuietly(stmt);
	}

	public static void upsertMany(Connection db, Collection<MarketStat> ss) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(UPSERT_SQL);
		int count = 0;
		for (MarketStat s : ss) {
			stmt.setInt(1, s.getRegionId());
			stmt.setInt(2, s.getTypeId());
			stmt.setLong(3, s.getMa30());
			stmt.setLong(4, s.getMa90());
			stmt.addBatch();
			count++;
			if (count % BATCH_SIZE == 0 || count == ss.size()) {
				stmt.executeBatch();
			}
		}
		Utils.closeQuietly(stmt);
	}

}
