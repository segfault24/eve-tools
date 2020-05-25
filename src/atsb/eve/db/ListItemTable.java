package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ListItemTable {

	private static final String SELECT_BY_ID_SQL = "SELECT `typeId`,`quantity` FROM dirtListItem WHERE `listId`=?";

	public static Map<Integer, Integer> getListItems(Connection db, int listId) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SELECT_BY_ID_SQL);
		stmt.setInt(1, listId);
		ResultSet rs = stmt.executeQuery();
		Map<Integer, Integer> items = new HashMap<Integer, Integer>();
		while (rs.next()) {
			int typeId = rs.getInt(1);
			int quantity = rs.getInt(2);
			if (items.containsKey(typeId)) {
				items.replace(typeId, items.get(typeId) + quantity);
			} else {
				items.put(typeId, quantity);
			}
		}
		return items;
	}

}