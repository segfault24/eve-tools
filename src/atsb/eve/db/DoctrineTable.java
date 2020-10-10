package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import atsb.eve.model.Doctrine;
import atsb.eve.util.Utils;

public class DoctrineTable {

	private static final String UPSERT_SQL = "INSERT INTO doctrine (`doctrine`,`listId`,`locationId`,`quantity`,`target`,`lowestPrice`) VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE `quantity`=VALUES(`quantity`), `lowestPrice`=VALUES(`lowestPrice`)";
	private static final String SELECT_ALL_SQL = "SELECT `doctrine`,`listId`,`locationId`,`quantity`,`target`,`lowestPrice` FROM doctrine";

	public static void upsert(Connection db, Doctrine d) throws SQLException {
		PreparedStatement stmt;
		stmt = db.prepareStatement(UPSERT_SQL);
		stmt.setInt(1, d.getDoctrineId());
		stmt.setInt(2, d.getListId());
		stmt.setLong(3, d.getLocationId());
		stmt.setInt(4, d.getQuantity());
		stmt.setInt(5, d.getTarget());
		stmt.setDouble(6, d.getLowestPrice());
		stmt.execute();
		Utils.closeQuietly(stmt);
	}

	public static List<Doctrine> getAllDoctrines(Connection db) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SELECT_ALL_SQL);
		ResultSet rs = stmt.executeQuery();
		List<Doctrine> doctrines = new ArrayList<Doctrine>();
		while (rs.next()) {
			Doctrine d = new Doctrine();
			d.setDoctrineId(rs.getInt(1));
			d.setListId(rs.getInt(2));
			d.setLocationId(rs.getLong(3));
			d.setQuantity(rs.getInt(4));
			d.setTarget(rs.getInt(5));
			d.setLowestPrice(rs.getDouble(6));
			doctrines.add(d);
		}
		Utils.closeQuietly(rs);
		Utils.closeQuietly(stmt);
		return doctrines;
	}

}
