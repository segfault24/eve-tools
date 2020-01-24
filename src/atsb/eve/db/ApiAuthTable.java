package atsb.eve.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import atsb.eve.model.OAuthUser;
import atsb.eve.util.Utils;

/**
 * 
 * 
 * @author austin
 */
public class ApiAuthTable {

	private static final String SELECT_BYKEYID_SQL = "SELECT `userId`,`charId`,`token`,`expires`,`refresh` FROM dirtApiAuth WHERE `keyId`=?;";
	private static final String SELECT_BYCHARID_SQL = "SELECT `keyId`,`userId`,`token`,`expires`,`refresh` FROM dirtApiAuth WHERE `charId`=?;";
	private static final String SELECT_ALL_SQL = "SELECT `charId` FROM dirtApiAuth";
	private static final String UPDATE_SQL = "UPDATE dirtApiAuth SET `token`=?, `expires`=?, `refresh`=? WHERE `keyId`=?;";

	public static OAuthUser getUserByKeyId(Connection db, int keyId) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SELECT_BYKEYID_SQL);
		stmt.setInt(1, keyId);
		ResultSet rs = stmt.executeQuery();

		OAuthUser oau = null;
		if (rs.next()) {
			oau = new OAuthUser();
			oau.setKeyId(keyId);
			oau.setUserId(rs.getInt("userId"));
			oau.setCharId(rs.getInt("charId"));
			oau.setAuthToken(rs.getString("token"));
			oau.setTokenExpires(rs.getTimestamp("expires"));
			oau.setRefreshToken(rs.getString("refresh"));
		}

		Utils.closeQuietly(rs);
		Utils.closeQuietly(stmt);

		return oau;
	}

	public static OAuthUser getUserByCharId(Connection db, int charId) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SELECT_BYCHARID_SQL);
		stmt.setInt(1, charId);
		ResultSet rs = stmt.executeQuery();

		OAuthUser oau = null;
		if (rs.next()) {
			oau = new OAuthUser();
			oau.setKeyId(rs.getInt("keyId"));
			oau.setUserId(rs.getInt("userId"));
			oau.setCharId(charId);
			oau.setAuthToken(rs.getString("token"));
			oau.setTokenExpires(rs.getTimestamp("expires"));
			oau.setRefreshToken(rs.getString("refresh"));
		}

		Utils.closeQuietly(rs);
		Utils.closeQuietly(stmt);

		return oau;
	}

	public static List<Integer> getAllCharacters(Connection db) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(SELECT_ALL_SQL);
		ResultSet rs = stmt.executeQuery();

		List<Integer> charIds = new ArrayList<Integer>();
		while (rs.next()) {
			charIds.add(rs.getInt("charId"));
		}

		Utils.closeQuietly(rs);
		Utils.closeQuietly(stmt);

		return charIds;
	}

	public static void update(Connection db, OAuthUser oau) throws SQLException {
		PreparedStatement stmt = db.prepareStatement(UPDATE_SQL);
		stmt.setString(1, oau.getAuthToken());
		stmt.setTimestamp(2, oau.getTokenExpires());
		stmt.setString(3, oau.getRefreshToken());
		stmt.setInt(4, oau.getKeyId());
		stmt.execute();
		Utils.closeQuietly(stmt);
	}

}
