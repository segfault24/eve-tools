package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

import atsb.eve.model.MarketOrder;
import atsb.eve.util.Utils;

public class MarketOrderTable {

	private static final String UPSERT_SQL = "INSERT INTO marketorder ("
			+ "`issued`,`range`,`isBuyOrder`,`duration`,`orderId`,`volumeRemain`,`minVolume`,"
			+ "`typeId`,`volumeTotal`,`locationId`,`price`,`regionId`,`retrieved`"
			+ ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE "
			+ "`volumeRemain`=VALUES(`volumeRemain`),`price`=VALUES(`price`),"
			+ "`retrieved`=VALUES(`retrieved`),`issued`=VALUES(`issued`)";
	private static final String DELETE_REGION_SQL = "DELETE FROM marketorder WHERE `regionId`=? AND `retrieved`<?";
	private static final String DELETE_SQL = "DELETE FROM marketorder WHERE retrieved<?";

	private static final int BATCH_SIZE = 1000;

	public static void upsertMany(Connection db, Collection<MarketOrder> os) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(UPSERT_SQL);
		int count = 0;
		for (MarketOrder o : os) {
			stmt.setTimestamp(1, o.getIssued());
			stmt.setString(2, o.getRange());
			stmt.setBoolean(3, o.isBuyOrder());
			stmt.setInt(4, o.getDuration());
			stmt.setLong(5, o.getOrderId());
			stmt.setInt(6, o.getVolumeRemain());
			stmt.setInt(7, o.getMinVolume());
			stmt.setInt(8, o.getTypeId());
			stmt.setInt(9, o.getVolumeTotal());
			stmt.setLong(10, o.getLocationId());
			stmt.setDouble(11, o.getPrice());
			stmt.setInt(12, o.getRegion());
			stmt.setTimestamp(13, o.getRetrieved());
			stmt.addBatch();
			count++;
			if (count % BATCH_SIZE == 0 || count == os.size()) {
				stmt.executeBatch();
			}
		}
		Utils.closeQuietly(stmt);
	}

	public static int deleteOldOrdersByRegion(Connection db, int region, Timestamp olderThan) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(DELETE_REGION_SQL);
		stmt.setInt(1, region);
		stmt.setTimestamp(2, olderThan);
		int retval = stmt.executeUpdate();
		Utils.closeQuietly(stmt);
		return retval;
	}

	public static int deleteOldOrders(Connection db, Timestamp olderThan) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(DELETE_SQL);
		stmt.setTimestamp(1, olderThan);
		int retval = stmt.executeUpdate();
		Utils.closeQuietly(stmt);
		return retval;
	}

}
