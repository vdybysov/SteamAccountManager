package net.sfka.sac.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import net.sfka.sac.sentryfiles.PackedSentryFile;

public class BackupUtils {

	public static void injectSentrys(File steamdir, List<PackedSentryFile> sentrys) {
		for (File f : steamdir.listFiles()) {
			if (f.getName().toLowerCase().contains("ssfn") && f.length() == 2048) {
				try {
					Files.delete(f.toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		for (PackedSentryFile psf : sentrys) {
			try {
				File sentryFile = new File(steamdir + "\\" + psf.getFilename());
				sentryFile.createNewFile();
				FileOutputStream fos = new FileOutputStream(sentryFile);
				fos.write(psf.getBytes());
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void backupSteam(File steamdir, File backupDir) {
		File loginusers = new File(steamdir + "\\config\\loginusers.vdf");
		if (loginusers.exists()) {
			try {
				copy(loginusers, backupDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File config = new File(steamdir + "\\config\\config.vdf");
		if (config.exists()) {
			try {
				copy(config, backupDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File appdata = new File(steamdir + "\\config\\SteamAppData.vdf");
		if (appdata.exists()) {
			try {
				copy(appdata, backupDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (File f : steamdir.listFiles()) {
			if (f.getName().toLowerCase().contains("ssfn") && f.length() == 2048) {
				try {
					copy(f, backupDir);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void restoreSteam(File steamdir, File backupDir) {
		File loginusers = new File(steamdir + "\\config\\loginusers.vdf");
		File loginusersBackup = new File(backupDir + "\\loginusers.vdf");
		if (loginusersBackup.exists()) {
			try {
				copy(loginusersBackup, loginusers);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File config = new File(steamdir + "\\config\\config.vdf");
		File configBackup = new File(backupDir + "\\config.vdf");
		if (loginusersBackup.exists()) {
			try {
				copy(configBackup, config);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File appdata = new File(steamdir + "\\config\\SteamAppData.vdf");
		File appdataBackup = new File(backupDir + "\\SteamAppData.vdf");
		if (loginusersBackup.exists()) {
			try {
				copy(appdataBackup, appdata);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (File f : backupDir.listFiles()) {
			if (f.getName().toLowerCase().contains("ssfn") && f.length() == 2048) {
				try {
					copy(f, steamdir);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void copy(File source, File dest) throws IOException {
		if (dest.isDirectory()) {
			dest = new File(dest + "\\" + source.getName());
		}
		dest.createNewFile();
		FileOutputStream fos = new FileOutputStream(dest);
		Files.copy(source.toPath(), fos);
		fos.close();
	}

}
