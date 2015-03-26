package net.sfka.sac.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.sfka.sac.accounts.SteamAccount;
import net.sfka.sac.accounts.SteamAccountsSorter;
import net.sfka.sac.backups.BackupsSorter;
import net.sfka.sac.backups.SteamBackup;
import net.sfka.sac.sentryfiles.PackedSentryFile;
import net.sfka.sac.sentryfiles.SentryFilesSorter;
import net.sfka.sac.ui.CheckSecurityPasswordDialog;
import net.sfka.sac.ui.MainWindow;
import net.sfka.sac.ui.SetSecurityPasswordDialog;
import net.sfka.sac.ui.SpecifySteamDirDialog;
import net.sfka.sac.utils.BackupUtils;
import net.sfka.sac.utils.ProcessesUtils;

import org.apache.commons.lang3.SerializationUtils;

public class SteamAccountManager {

	private CopyOnWriteArrayList<SteamAccount> accounts;
	private CopyOnWriteArrayList<PackedSentryFile> sentryFiles;
	private CopyOnWriteArrayList<SteamBackup> backups;
	private File rootDir, accsDir, sentrysDir, backupsDir, dataFile;
	private SAMData data;
	private String version;
	private MainWindow ui;
	private SteamAccount selectedAccount;
	private SteamAccountsSorter accountSorter;
	private SentryFilesSorter sentrysSorter;
	private BackupsSorter backupsSorter;

	public SteamAccountManager() {
		accounts = new CopyOnWriteArrayList<>();
		sentryFiles = new CopyOnWriteArrayList<>();
		backups = new CopyOnWriteArrayList<>();
		accountSorter = new SteamAccountsSorter();
		sentrysSorter = new SentryFilesSorter();
		backupsSorter = new BackupsSorter();
		this.version = Main.VERSION;
		rootDir = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\Sandfield\\Steam Account Manager\\");
		this.dataFile = new File(rootDir + "\\samdata");
		backupsDir = new File(rootDir + "\\backups\\");
		if (!backupsDir.exists()) {
			backupsDir.mkdirs();
		}
		sentrysDir = new File(rootDir + "\\sentry-files\\");
		if (!sentrysDir.exists()) {
			sentrysDir.mkdirs();
		}
		sentryFiles.addAll(parseSentryFiles(sentrysDir));
		accsDir = new File(rootDir + "\\accounts\\");
		if (!accsDir.exists()) {
			accsDir.mkdirs();
		}
		accounts.addAll(parseAccs(accsDir));
		if (dataFile.exists()) {
			try {
				FileInputStream fis = new FileInputStream(dataFile);
				data = SerializationUtils.deserialize(fis);
				fis.close();
			} catch (Exception e) {
				System.out.println("Error opening SteamAccountManager data file!");
				data = new SAMData(true, null, null, 0, 0);
			}
		} else {
			System.out.println("SteamAccountManager data file doesn't exist!");
			data = new SAMData(true, null, null, 0, 0);
		}
		backups.addAll(parseBackups(backupsDir));
		if (backups.size() == 0) {
			try {
				addBackup(new SteamBackup(data.getSteamdir()));
			} catch (IOException e) {
				System.out.println("Error backing up Steam!");
			}
		}
		sortAccounts(0, false);
		sortSentrys(0, false);
		sortBackups(0, false);
		if (data.getSteamdir() == null) {
			new SpecifySteamDirDialog(this).setVisible(true);
		} else {
			initPasswordCheckUI();
		}
		saveData();
	}

	public void sortAccounts(int by, boolean descend) {
		List<SteamAccount> toSort = new ArrayList<>();
		toSort.addAll(accounts);
		accounts = new CopyOnWriteArrayList<>();
		accounts.addAll(accountSorter.sort(toSort, by, descend));
		refreshUI();
	}

	public void sortSentrys(int by, boolean descend) {
		List<PackedSentryFile> toSort = new ArrayList<>();
		toSort.addAll(sentryFiles);
		sentryFiles = new CopyOnWriteArrayList<>();
		sentryFiles.addAll(sentrysSorter.sort(toSort, by, descend));
		refreshUI();
	}

	public void sortBackups(int by, boolean descend) {
		List<SteamBackup> toSort = new ArrayList<>();
		toSort.addAll(backups);
		backups = new CopyOnWriteArrayList<>();
		backups.addAll(backupsSorter.sort(toSort, by, descend));
		refreshUI();
	}

	public void openURL(String url) {
		try {
			Object o = Class.forName("java.awt.Desktop").getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
			o.getClass().getMethod("browse", new Class[] {
				URI.class
			}).invoke(o, new Object[] {
				new URI(url)
			});
		} catch (Throwable e) {
		}
	}

	public void exportAccount(SteamAccount acc, File file) throws IOException {
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(SerializationUtils.serialize(acc));
		fos.close();
	}

	private List<SteamBackup> parseBackups(File dir) {
		List<SteamBackup> parsed = new ArrayList<>();
		for (File f : dir.listFiles()) {
			try {
				SteamBackup b = SerializationUtils.deserialize(Files.readAllBytes(f.toPath()));
				parsed.add(b);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return parsed;
	}

	public void addBackup(SteamBackup backup) {
		for (SteamBackup b : backups) {
			if (b.getName().equalsIgnoreCase(backup.getName())) {
				saveBackups();
				refreshUI();
				return;
			}
		}
		backups.add(backup);
		refreshUI();
		saveBackups();
	}

	public void exit() {
		ui.dispose();
		saveData();
		saveAccounts();
		saveSentrys();
		saveBackups();
		System.exit(0);
	}

	public void saveSentrys() {
		for (File f : sentrysDir.listFiles()) {
			try {
				Files.delete(f.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (PackedSentryFile s : sentryFiles) {
			try {
				File accFile = new File(sentrysDir + "\\" + s.getName() + "_" + s.getFilename());
				accFile.createNewFile();
				FileOutputStream fos = new FileOutputStream(accFile);
				fos.write(SerializationUtils.serialize(s));
				fos.close();
			} catch (IOException e) {
				System.out.println("Error saving " + s.getName());
				e.printStackTrace();
			}
		}
	}

	public void saveAccounts() {
		for (File f : accsDir.listFiles()) {
			try {
				Files.delete(f.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (SteamAccount acc : accounts) {
			try {
				File accFile = new File(accsDir + "\\" + acc.getLogin() + ".sa");
				accFile.createNewFile();
				FileOutputStream fos = new FileOutputStream(accFile);
				fos.write(SerializationUtils.serialize(acc));
				fos.close();
			} catch (IOException e) {
				System.out.println("Error saving " + acc.getLogin());
				e.printStackTrace();
			}
		}
	}

	public boolean saveData() {
		try {
			dataFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(dataFile);
			fos.write(SerializationUtils.serialize(data));
			fos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error saving data!");
			return false;
		}
	}

	public void saveBackups() {
		for (File f : backupsDir.listFiles()) {
			try {
				Files.delete(f.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (SteamBackup b : backups) {
			try {
				File bf = new File(backupsDir + "\\" + cleanLine(b.getName()).replace(":", ""));
				bf.createNewFile();
				FileOutputStream fos = new FileOutputStream(bf);
				fos.write(SerializationUtils.serialize(b));
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public PackedSentryFile getSentryFileByFilename(String filename) {
		for (PackedSentryFile psf : sentryFiles) {
			if (psf.getFilename().equals(filename))
				return psf;
		}
		return null;
	}

	public SteamAccount getSteamAccountByName(String name) {
		for (SteamAccount acc : accounts) {
			if (acc.getLogin().equalsIgnoreCase(name))
				return acc;
		}
		return null;
	}

	public SteamBackup getSteamBackupByName(String name) {
		for (SteamBackup b : backups) {
			if (b.getName().equalsIgnoreCase(name))
				return b;
		}
		return null;
	}

	public void initUI() {
		ui = new MainWindow(this);
		ui.setVisible(true);
		refreshUI();
	}

	public void initPasswordCheckUI() {
		if (data.getSecurityPassword() == null) {
			new SetSecurityPasswordDialog(this).setVisible(true);
		} else {
			if (data.isCheckPassword()) {
				new CheckSecurityPasswordDialog(this).setVisible(true);
			} else {
				initUI();
			}
		}
	}

	public void addSentry(PackedSentryFile psf) {
		for (PackedSentryFile cs : sentryFiles) {
			if (cs.getFilename().equalsIgnoreCase(psf.getFilename())) {
				cs.setBytes(psf.getBytes());
				return;
			}
		}
		sentryFiles.add(psf);
	}

	public void refreshUI() {
		if (ui != null) {
			ui.refreshAccountsTable(accounts);
			ui.refreshSentrysTable(sentryFiles);
			ui.refreshBackupsTable(backups);
			ui.setSelectedAccount((selectedAccount == null) ? "..." : selectedAccount.getLogin());
		}
	}

	private static String cleanLine(String line) {
		return line.replace("	", "").replace(" ", "").replace("\"", "");
	}

	public static List<SteamAccount> parseSteamLogins(File steamdir) throws IOException {
		File config = new File(steamdir + "\\config\\config.vdf");
		File loginusers = new File(steamdir + "\\config\\loginusers.vdf");
		List<SteamAccount> steamLogins = new ArrayList<>();
		String configString = "";
		if (config.exists()) {
			try {
				BufferedReader r = new BufferedReader(new FileReader(config));
				if ((configString = r.readLine()) != null) {
					String line;
					while ((line = r.readLine()) != null) {
						configString += "\n" + line;
					}
				}
				r.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		List<PackedSentryFile> sentrys = new ArrayList<>();

		for (File f : steamdir.listFiles()) {
			if (f.getName().toLowerCase().contains("ssfn")) {
				if (f.length() == 2048) {
					try {
						sentrys.add(new PackedSentryFile(f));
					} catch (IOException e) {

					}
				}
			}
		}

		BufferedReader lur = new BufferedReader(new FileReader(loginusers));
		String line;
		CopyOnWriteArrayList<String> loginUsersLines = new CopyOnWriteArrayList<>();
		while ((line = lur.readLine()) != null) {
			loginUsersLines.add(line);
		}
		lur.close();
		for (int i = 0; i < loginUsersLines.size(); i++) {
			String cl = loginUsersLines.get(i);
			String idS = cleanLine(cl);
			if (idS.length() == 17) {
				try {
					Long.parseLong(idS);
					try {
						String name = "undefined";
						long timestamp = 0;
						String ncl;
						int n = i + 1;
						while (!(ncl = loginUsersLines.get(n).toLowerCase()).contains("}")) {
							if (ncl.contains("\"accountname\"")) {
								String accname = ncl.split("\"accountname\"")[1];
								accname = cleanLine(accname);
								name = accname;
							} else if (ncl.contains("\"timestamp\"")) {
								String ts = ncl.split("\"timestamp\"")[1];
								ts = cleanLine(ts);
								try {
									timestamp = Long.parseLong(ts);
								} catch (NumberFormatException nfe) {
									timestamp = 0;
								}
							}
							n++;
						}
						steamLogins.add(new SteamAccount(name, "password", idS, configString, "", sentrys, timestamp * 1000));
					} catch (NumberFormatException nfe) {
					}
				} catch (NumberFormatException e) {

				}
			}
		}
		return steamLogins;
	}

	private List<SteamAccount> parseAccs(File dir) {
		List<SteamAccount> parsed = new ArrayList<>();
		for (File f : dir.listFiles()) {
			try {
				FileInputStream fis = new FileInputStream(f);
				SteamAccount sa = SerializationUtils.deserialize(fis);
				fis.close();
				for (PackedSentryFile sf : sa.getSentryFiles()) {
					if (getSentryFileByFilename(sf.getFilename()) == null) {
						sf.setName(sa.getLogin() + "_" + sf.getName());
						addSentry(sf);
					}
				}
				parsed.add(sa);
			} catch (Exception e) {
				System.out.println("Error opening " + f + " as SteamAccount file!");
			}
		}
		return parsed;
	}

	private List<PackedSentryFile> parseSentryFiles(File dir) {
		List<PackedSentryFile> parsed = new ArrayList<>();
		for (File f : dir.listFiles()) {
			try {
				FileInputStream fis = new FileInputStream(f);
				PackedSentryFile sf = SerializationUtils.deserialize(fis);
				fis.close();
				parsed.add(sf);
			} catch (Exception e) {
				System.out.println("Error opening " + f + " as PackedSentryFile!");
			}
		}
		return parsed;
	}

	public void launchSteam() {
		selectedAccount.setLastLogin(System.currentTimeMillis());
		BackupUtils.injectSentrys(data.getSteamdir(), selectedAccount.getSentryFiles());
		ProcessesUtils.launchSteam(data.getSteamdir() + "\\Steam.exe", selectedAccount.getLogin(), selectedAccount.getDecodedPassword());
	}

	public void addAccount(SteamAccount acc) {
		SteamAccount sa = getSteamAccountByName(acc.getLogin());
		if (sa != null) {
			sa = acc;
		} else {
			accounts.add(acc);
		}
		for (PackedSentryFile sen : acc.getSentryFiles()) {
			addSentry(sen);
		}
		refreshUI();
	}

	public List<SteamAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(CopyOnWriteArrayList<SteamAccount> accounts) {
		this.accounts = accounts;
	}

	public File getRootDir() {
		return rootDir;
	}

	public void setRootDir(File rootDir) {
		this.rootDir = rootDir;
	}

	public SAMData getData() {
		return data;
	}

	public void setData(SAMData data) {
		this.data = data;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void readData() throws IOException {
		data = SerializationUtils.deserialize(Files.readAllBytes(dataFile.toPath()));
	}

	public MainWindow getUi() {
		return ui;
	}

	public void setUi(MainWindow ui) {
		this.ui = ui;
	}

	public CopyOnWriteArrayList<PackedSentryFile> getSentryFiles() {
		return sentryFiles;
	}

	public void setSentryFiles(CopyOnWriteArrayList<PackedSentryFile> sentryFiles) {
		this.sentryFiles = sentryFiles;
	}

	public SteamAccount getSelectedAccount() {
		return selectedAccount;
	}

	public void setSelectedAccount(SteamAccount selectedAccount) {
		this.selectedAccount = selectedAccount;
		if (ui != null) {
			if (selectedAccount != null) {
				ui.setSelectedAccount(selectedAccount.getLogin());
			} else {
				ui.setSelectedAccount("...");
			}
		}
	}

	public void deleteAccount(SteamAccount acc) {
		if (acc.equals(selectedAccount))
			setSelectedAccount(null);
		if (accounts.contains(acc)) {
			accounts.remove(acc);
		}
		saveAccounts();
	}

	public void deleteSentryFile(PackedSentryFile psf) {
		if (sentryFiles.contains(psf)) {
			sentryFiles.remove(psf);
			for (SteamAccount acc : accounts) {
				if (acc.getSentryFiles().contains(psf)) {
					List<PackedSentryFile> sentrys = new ArrayList<>();
					for (PackedSentryFile sf : acc.getSentryFiles()) {
						if (!sf.equals(psf)) {
							sentrys.add(sf);
						}
					}
					acc.setSentryFiles(sentrys);
				}
			}
		}
		saveSentrys();
	}

	public void deleteBackup(SteamBackup backup) {
		if (backups.contains(backup))
			backups.remove(backup);
		saveBackups();
	}

	public CopyOnWriteArrayList<SteamBackup> getBackups() {
		return backups;
	}

	public void setBackups(CopyOnWriteArrayList<SteamBackup> backups) {
		this.backups = backups;
	}

	public SteamAccountsSorter getAccountSorter() {
		return accountSorter;
	}

	public void setAccountSorter(SteamAccountsSorter accountSorter) {
		this.accountSorter = accountSorter;
	}

	public SentryFilesSorter getSentrysSorter() {
		return sentrysSorter;
	}

	public void setSentrysSorter(SentryFilesSorter sentrysSorter) {
		this.sentrysSorter = sentrysSorter;
	}

	public BackupsSorter getBackupsSorter() {
		return backupsSorter;
	}

	public void setBackupsSorter(BackupsSorter backupsSorter) {
		this.backupsSorter = backupsSorter;
	}

}
