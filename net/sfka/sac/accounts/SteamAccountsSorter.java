package net.sfka.sac.accounts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SteamAccountsSorter {

	public static final int NAME = 0;
	public static final int PASSWORD = 1;
	public static final int LAST_LOGIN = 2;
	public static final int DESCRIPTION = 3;

	private List<SteamAccount> sorting, added;

	public List<SteamAccount> sort(List<SteamAccount> accounts, int sortBy, boolean descend) {
		sorting = accounts;
		added = new ArrayList<>();
		List<SteamAccount> sorted = new ArrayList<>();
		sorted = accounts;
		switch (sortBy) {
		case NAME:
			sorted = sortByName();
			break;
		case PASSWORD:
			sorted = sortByPassword();
			break;
		case LAST_LOGIN:
			sorted = sortByLastLogin();
			break;
		case DESCRIPTION:
			sorted = sortByDescription();
			break;
		default:
			break;
		}
		if (!descend)
			Collections.reverse(sorted);
		return sorted;
	}

	private List<String> getAccsNames() {
		List<String> names = new ArrayList<>();
		for (SteamAccount scl : sorting)
			names.add(scl.getLogin().toLowerCase());
		return names;
	}

	private List<SteamAccount> sortByName() {
		List<SteamAccount> sorted = new ArrayList<>();
		List<String> sortedNames = getAccsNames();
		Collections.sort(sortedNames);
		for (String name : sortedNames) {
			SteamAccount scl = getAccountByName(name);
			sorted.add(scl);
			added.add(scl);
		}
		return sorted;
	}

	private SteamAccount getAccountByName(String name) {
		for (SteamAccount scl : sorting) {
			if (scl.getLogin().equalsIgnoreCase(name) && !added.contains(scl))
				return scl;
		}
		return null;
	}

	private List<String> getAccsPasswords() {
		List<String> passwords = new ArrayList<>();
		for (SteamAccount scl : sorting)
			passwords.add(scl.getDecodedPassword());
		return passwords;
	}

	private List<SteamAccount> sortByPassword() {
		List<SteamAccount> sorted = new ArrayList<>();
		List<String> sortedpasswords = getAccsPasswords();
		Collections.sort(sortedpasswords);
		for (String pass : sortedpasswords) {
			SteamAccount scl = getAccountByPassword(pass);
			sorted.add(scl);
			added.add(scl);
		}
		return sorted;
	}

	private SteamAccount getAccountByPassword(String pass) {
		for (SteamAccount scl : sorting) {
			if (scl.getDecodedPassword().equals(pass) && !added.contains(scl))
				return scl;
		}
		return null;
	}

	private List<Long> getAccsLastLogins() {
		List<Long> lastLogins = new ArrayList<>();
		for (SteamAccount sa : sorting)
			lastLogins.add(sa.getLastLogin());
		return lastLogins;
	}

	private List<SteamAccount> sortByLastLogin() {
		List<SteamAccount> sorted = new ArrayList<>();
		List<Long> sortedLastLogins = getAccsLastLogins();
		Collections.sort(sortedLastLogins);
		for (Long ll : sortedLastLogins) {
			SteamAccount scl = getAccountByLastLogin(ll);
			sorted.add(scl);
			added.add(scl);
		}
		return sorted;
	}

	private SteamAccount getAccountByLastLogin(Long ll) {
		for (SteamAccount sa : sorting) {
			if (sa.getLastLogin() == ll && !added.contains(sa))
				return sa;
		}
		return null;
	}

	private List<String> getAccsDecriptions() {
		List<String> decriptions = new ArrayList<>();
		for (SteamAccount scl : sorting)
			decriptions.add(scl.getDescription());
		return decriptions;
	}

	private List<SteamAccount> sortByDescription() {
		List<SteamAccount> sorted = new ArrayList<>();
		List<String> sortedDescriptions = getAccsDecriptions();
		Collections.sort(sortedDescriptions);
		for (String d : sortedDescriptions) {
			SteamAccount scl = getAccountByDecription(d);
			sorted.add(scl);
			added.add(scl);
		}
		return sorted;
	}

	private SteamAccount getAccountByDecription(String desc) {
		for (SteamAccount scl : sorting) {
			if (scl.getDescription().equals(desc) && !added.contains(scl))
				return scl;
		}
		return null;
	}

}
