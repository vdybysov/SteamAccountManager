package net.sfka.sac.backups;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BackupsSorter {

	public static final int NAME = 0;
	public static final int CREATED = 1;

	private List<SteamBackup> sorting, added;

	public List<SteamBackup> sort(List<SteamBackup> accounts, int sortBy, boolean descend) {
		sorting = accounts;
		added = new ArrayList<>();
		List<SteamBackup> sorted = new ArrayList<>();
		sorted = accounts;
		switch (sortBy) {
		case NAME:
			sorted = sortByName();
			break;
		case CREATED:
			sorted = sortByTimeCreated();
			break;
		default:
			break;
		}
		if (!descend)
			Collections.reverse(sorted);
		return sorted;
	}

	private List<String> getBackupsNames() {
		List<String> names = new ArrayList<>();
		for (SteamBackup scl : sorting)
			names.add(scl.getName().toLowerCase());
		return names;
	}

	private List<SteamBackup> sortByName() {
		List<SteamBackup> sorted = new ArrayList<>();
		List<String> sortedNames = getBackupsNames();
		Collections.sort(sortedNames);
		for (String name : sortedNames) {
			SteamBackup scl = getBackupByName(name);
			sorted.add(scl);
			added.add(scl);
		}
		return sorted;
	}

	private SteamBackup getBackupByName(String name) {
		for (SteamBackup scl : sorting) {
			if (scl.getName().equalsIgnoreCase(name) && !added.contains(scl))
				return scl;
		}
		return null;
	}

	private List<Long> getBackupsTimestamps() {
		List<Long> ts = new ArrayList<>();
		for (SteamBackup scl : sorting)
			ts.add(scl.getTimestamp());
		return ts;
	}

	private List<SteamBackup> sortByTimeCreated() {
		List<SteamBackup> sorted = new ArrayList<>();
		List<Long> sortedTimestamps = getBackupsTimestamps();
		Collections.sort(sortedTimestamps);
		for (Long ts : sortedTimestamps) {
			SteamBackup scl = getSentryByTimestamp(ts);
			sorted.add(scl);
			added.add(scl);
		}
		return sorted;
	}

	private SteamBackup getSentryByTimestamp(Long ts) {
		for (SteamBackup scl : sorting) {
			if (scl.getTimestamp() == ts && !added.contains(scl))
				return scl;
		}
		return null;
	}

}
