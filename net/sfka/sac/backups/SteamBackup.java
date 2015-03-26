package net.sfka.sac.backups;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.sfka.sac.sentryfiles.PackedSentryFile;
import net.sfka.sac.utils.TimeUtils;

public class SteamBackup implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private byte[] steamAppData, loginusers, config;
	private List<PackedSentryFile> sentryFiles;
	private long timestamp;

	public SteamBackup(File steamdir) throws IOException {
		steamAppData = Files.readAllBytes(new File(steamdir + "\\config\\SteamAppData.vdf").toPath());
		loginusers = Files.readAllBytes(new File(steamdir + "\\config\\loginusers.vdf").toPath());
		config = Files.readAllBytes(new File(steamdir + "\\config\\config.vdf").toPath());
		sentryFiles = new ArrayList<>();
		for (File f : steamdir.listFiles()) {
			if (f.getName().toLowerCase().contains("ssfn") && f.length() == 2048) {
				try {
					sentryFiles.add(new PackedSentryFile(f));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		this.timestamp = System.currentTimeMillis();
		Properties date = TimeUtils.timeToDate(timestamp);
		this.name = "Backup from " + date.getProperty("day") + " " + TimeUtils.getMonth(Integer.parseInt(date.getProperty("month"))) + " " + date.getProperty("year") + " " + date.getProperty("hour") + ":" + date.getProperty("minute");
	}

	public String getTimestampAsString() {
		Properties date = TimeUtils.timeToDate(timestamp);
		return date.getProperty("day") + " " + TimeUtils.getMonth(Integer.parseInt(date.getProperty("month"))) + " " + date.getProperty("year") + " " + date.getProperty("hour") + ":" + date.getProperty("minute");
	}

	public void extract(File destination) throws IOException {
		if (destination.exists() && destination.isDirectory()) {
			new File(destination + "\\config\\").mkdirs();
			File steamAppDataFile = new File(destination + "\\config\\SteamAppData.vdf");
			File loginusersFile = new File(destination + "\\config\\loginusers.vdf");
			File configFile = new File(destination + "\\config\\config.vdf");
			steamAppDataFile.createNewFile();
			loginusersFile.createNewFile();
			configFile.createNewFile();
			FileOutputStream sadffos = new FileOutputStream(steamAppDataFile);
			FileOutputStream lufos = new FileOutputStream(loginusersFile);
			FileOutputStream cffos = new FileOutputStream(configFile);
			sadffos.write(steamAppData);
			lufos.write(loginusers);
			cffos.write(config);
			sadffos.close();
			lufos.close();
			cffos.close();
			for (File f : destination.listFiles()) {
				if (f.getName().toLowerCase().contains("ssfn") && f.length() == 2048) {
					try {
						Files.delete(f.toPath());
					} catch (IOException e) {

					}
				}
			}
			for (PackedSentryFile psf : sentryFiles) {
				File sf = new File(destination + "\\" + psf.getFilename());
				sf.createNewFile();
				FileOutputStream fos = new FileOutputStream(sf);
				fos.write(psf.getBytes());
				fos.close();
			}
		}
	}

	public SteamBackup(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getSteamAppData() {
		return steamAppData;
	}

	public void setSteamAppData(byte[] steamAppData) {
		this.steamAppData = steamAppData;
	}

	public byte[] getLoginusers() {
		return loginusers;
	}

	public void setLoginusers(byte[] loginusers) {
		this.loginusers = loginusers;
	}

	public byte[] getConfig() {
		return config;
	}

	public void setConfig(byte[] config) {
		this.config = config;
	}

	public List<PackedSentryFile> getSentryFiles() {
		return sentryFiles;
	}

	public void setSentryFiles(List<PackedSentryFile> sentryFiles) {
		this.sentryFiles = sentryFiles;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
