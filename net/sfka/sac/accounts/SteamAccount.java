package net.sfka.sac.accounts;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import net.sfka.sac.sentryfiles.PackedSentryFile;
import net.sfka.sac.utils.EncodingUtils;
import net.sfka.sac.utils.TimeUtils;

public class SteamAccount implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String profileID, login, password, config, description;
	private List<PackedSentryFile> sentryFiles;
	private long lastLogin;

	public SteamAccount(String login, String password, String profileID, String config, String description, List<PackedSentryFile> sentryFiles, long lastLogin) {
		this.login = login;
		this.profileID = profileID;
		this.password = EncodingUtils.encode(password);
		this.config = config;
		this.description = description;
		this.sentryFiles = sentryFiles;
		this.lastLogin = lastLogin;
	}
	
	public String getProfileURL() {
		String steamc = "http://steamcommunity.com/";
		try {
			Long.parseLong(profileID);
			return steamc + "user/" + profileID;
		} catch (NumberFormatException e) {
			return steamc + "id/" + profileID;
		}
	}

	public String[] asRow() {
		Properties date = TimeUtils.timeToDate(lastLogin);
		String lastLoginString = date.getProperty("day") + " " + TimeUtils.getMonth(Integer.parseInt(date.getProperty("month"))) + " " + date.getProperty("year") + " " + date.getProperty("hour") + ":" + date.getProperty("minute");
		return new String[] {
		login, EncodingUtils.decode(password), lastLoginString, description
		};
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public String getDecodedPassword() {
		return EncodingUtils.decode(password);
	}

	public void setRawPassword(String password) {
		this.password = EncodingUtils.encode(password);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public List<PackedSentryFile> getSentryFiles() {
		return sentryFiles;
	}

	public void setSentryFiles(List<PackedSentryFile> sentryFiles) {
		this.sentryFiles = sentryFiles;
	}

	public long getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(long lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProfileID() {
		return profileID;
	}

	public void setProfileID(String profileID) {
		this.profileID = profileID;
	}

}
