package atsb.eve.model;

import java.sql.Timestamp;

/**
 * @author austin
 */
public class OAuthUser {

	private static final int EXPIRES_WITHIN = 60000; // milliseconds

	protected int keyId;
	private int userId;
	private int charId;
	private String charName;
	private String charHash;
	private String authToken;
	private String tokenType;
	private Timestamp tokenExpires;
	private String refreshToken;

	public int getKeyId() {
		return keyId;
	}

	public int getUserId() {
		return userId;
	}

	public int getCharId() {
		return charId;
	}

	public String getCharName() {
		return charName;
	}

	public String getCharHash() {
		return charHash;
	}

	public String getTokenType() {
		return tokenType;
	}

	public Timestamp getTokenExpires() {
		return tokenExpires;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getAuthToken() {
		return authToken;
	}

	public boolean isExpired() {
		return tokenExpires.before(new Timestamp(System.currentTimeMillis() + EXPIRES_WITHIN));
	}

	public static int getExpiresWithin() {
		return EXPIRES_WITHIN;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setCharId(int charId) {
		this.charId = charId;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}

	public void setCharHash(String charHash) {
		this.charHash = charHash;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public void setTokenExpires(Timestamp tokenExpires) {
		this.tokenExpires = tokenExpires;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
