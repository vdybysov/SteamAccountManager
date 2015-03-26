package net.sfka.sac.main;

import java.io.File;
import java.io.Serializable;

import net.sfka.sac.utils.EncodingUtils;

public class SAMData implements Serializable {

	private static final long serialVersionUID = 1L;

	private File steamdir;

	private char[] securityPassword;
	private boolean checkPassword;

	public SAMData(boolean checkPassword, char[] securityPassword, File steamdir, int windowPosX, int windowPosY) {
		this.steamdir = steamdir;
		this.securityPassword = securityPassword;
		this.checkPassword = checkPassword;
	}

	public File getSteamdir() {
		return steamdir;
	}

	public void setSteamdir(File steamdir) {
		this.steamdir = steamdir;
	}

	public char[] getSecurityPassword() {
		if (securityPassword != null) {
			return EncodingUtils.decode(String.valueOf(securityPassword)).toCharArray();
		} else {
			return null;
		}
	}

	public void setSecurityPassword(char[] securityPassword) {
		this.securityPassword = EncodingUtils.encode(String.valueOf(securityPassword)).toCharArray();
	}

	public boolean isCheckPassword() {
		return checkPassword;
	}

	public void setCheckPassword(boolean checkPassword) {
		this.checkPassword = checkPassword;
	}

}
