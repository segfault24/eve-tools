package atsb.eve.util;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {

	private static Logger log = LogManager.getLogger();

	private static final String SELECT_PROPERTY_SQL = "SELECT `propertyValue` FROM property WHERE `propertyName`=?";
	private static final String SELECT_KV_SQL = "SELECT `value` FROM kvStore WHERE `key`=?";
	private static final String UPSERT_KV_SQL = "INSERT INTO kvStore (`key`,`value`) VALUES (?,?) ON DUPLICATE KEY UPDATE `value`=VALUES(`value`)";
	private static final String DELETE_KV_SQL = "DELETE FROM kvStore WHERE `key`=?";
	private static final String SELECT_ETAG_SQL = "SELECT `etag` FROM apiReq WHERE `apiReqName`=?";
	private static final String UPSERT_ETAG_SQL = "INSERT INTO apiReq (`apiReqName`,`etag`) VALUES(?,?) ON DUPLICATE KEY UPDATE `apiReqName`=VALUES(`apiReqName`),`etag`=VALUES(`etag`)";

	public static void closeQuietly(AutoCloseable c) {
		if (c != null) {
			try {
				c.close();
			} catch (Exception e) {
				// do nothing
			}
		}
	}

	public static void closeQuietly(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
				// do nothing
			}
		}
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch(InterruptedException e) {
			// do nothing
		}
	}

	public static List<Integer> parseIntList(String input) {
		List<Integer> values = new ArrayList<Integer>();
		if (input != null && !input.isEmpty()) {
			for (String s : input.split(",")) {
				try {
					values.add(Integer.parseInt(s.trim()));
				} catch (NumberFormatException e) {
					log.warn("bad number specified in int list");
				}
			}
		}
		return values;
	}

	public static List<Long> parseLongList(String input) {
		List<Long> values = new ArrayList<Long>();
		if (input != null && !input.isEmpty()) {
			for (String s : input.split(",")) {
				try {
					values.add(Long.parseLong(s.trim()));
				} catch (NumberFormatException e) {
					log.warn("bad number specified in long list");
				}
			}
		}
		return values;
	}

	public static String getProperty(Connection db, String propertyName) {
		if (db == null || propertyName == null || propertyName.isEmpty()) {
			log.warn("The db and propertyName must be non-null and non-empty");
			return null;
		}

		String propertyValue = "";
		try {
			PreparedStatement stmt = db.prepareStatement(SELECT_PROPERTY_SQL);
			stmt.setString(1, propertyName);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				propertyValue = rs.getString("propertyValue");
			}
		} catch (SQLException e) {
			log.warn("Failed to read property '" + propertyName + "' from database", e);
		}

		return propertyValue;
	}

	public static int getIntProperty(Connection db, String propertyName) {
		return Integer.parseInt(getProperty(db, propertyName));
	}

	public static long getLongProperty(Connection db, String propertyName) {
		return Long.parseLong(getProperty(db, propertyName));
	}

	public static boolean getBoolProperty(Connection db, String propertyName) {
		String s = getProperty(db, propertyName);
		// try parsing as an int first
		try {
			int i = Integer.parseInt(s);
			return i == 0 ? false : true;
		} catch(NumberFormatException e) {
		}
		// if the int parse failed, try bool parse (defaults to false)
		return Boolean.parseBoolean(s);
	}

	public static String getKV(Connection db, String key) {
		if (db == null || key == null || key.isEmpty()) {
			log.warn("The db and key must be non-null and non-empty");
			return null;
		}
		String value = "";
		try {
			PreparedStatement stmt = db.prepareStatement(SELECT_KV_SQL);
			stmt.setString(1, key);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				value = rs.getString("value");
			}
		} catch (SQLException e) {
			log.warn("Failed to read kv '" + key + "' from database", e);
		}
		return value;
	}

	public static void putKV(Connection db, String key, String value) {
		if (db == null || key == null || key.isEmpty()) {
			log.warn("The db and key must be non-null and non-empty");
			return;
		}
		try {
			PreparedStatement stmt = db.prepareStatement(UPSERT_KV_SQL);
			stmt.setString(1, key);
			stmt.setString(2, value);
			stmt.execute();
		} catch (SQLException e) {
			log.warn("Failed to upsert kv '" + key + "' to database", e);
		}
	}

	public static void deleteKV(Connection db, String key) {
		if (db == null || key == null || key.isEmpty()) {
			log.warn("The db and key must be non-null and non-empty");
			return;
		}
		try {
			PreparedStatement stmt = db.prepareStatement(DELETE_KV_SQL);
			stmt.setString(1, key);
			stmt.execute();
		} catch (SQLException e) {
			log.warn("Failed to delete kv '" + key + "' from database", e);
		}
	}

	public static String getApiDatasource() {
		return "tranquility";
	}

	public static String getApiLanguage() {
		return "en-us";
	}

	public static String getEtag(Map<String, List<String>> a) {
		if (a == null) {
			return null;
		}
		List<String> h = a.get("Etag");
		if (h == null || h.isEmpty()) {
			return null;
		}
		return h.get(0).replaceAll("^\"|\"$", "");
	}

	public static String getEtag(Connection db, String apiReqName) {
		if (db == null || apiReqName == null) {
			return null;
		}
		try {
			PreparedStatement stmt = db.prepareStatement(SELECT_ETAG_SQL);
			stmt.setString(1, apiReqName);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			} else {
				return null;
			}
		} catch (SQLException e) {
			log.warn("Failed to retrieve etag for apiReqName=" + apiReqName, e);
			return null;
		}
	}

	public static void upsertEtag(Connection db, String apiReqName, String etag) {
		if (db == null || apiReqName == null || etag == null) {
			return;
		}
		try {
			PreparedStatement stmt = db.prepareStatement(UPSERT_ETAG_SQL);
			stmt.setString(1, apiReqName);
			stmt.setString(2, etag);
			stmt.execute();
		} catch (SQLException e) {
			log.warn("Failed to upsert etag for apiReqName=" + apiReqName, e);
		}
	}

}
