package net.sfka.sac.sentryfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SentryFilesSorter {

	public static final int NAME = 0;
	public static final int FILENAME = 1;

	private List<PackedSentryFile> sorting, added;

	public List<PackedSentryFile> sort(List<PackedSentryFile> accounts, int sortBy, boolean descend) {
		sorting = accounts;
		added = new ArrayList<>();
		List<PackedSentryFile> sorted = new ArrayList<>();
		sorted = accounts;
		switch (sortBy) {
		case NAME:
			sorted = sortByName();
			break;
		case FILENAME:
			sorted = sortByFilename();
			break;
		default:
			break;
		}
		if (!descend)
			Collections.reverse(sorted);
		return sorted;
	}

	private List<String> getSentrysNames() {
		List<String> names = new ArrayList<>();
		for (PackedSentryFile scl : sorting)
			names.add(scl.getName().toLowerCase());
		return names;
	}

	private List<PackedSentryFile> sortByName() {
		List<PackedSentryFile> sorted = new ArrayList<>();
		List<String> sortedNames = getSentrysNames();
		Collections.sort(sortedNames);
		for (String name : sortedNames) {
			PackedSentryFile scl = getSentryByName(name);
			sorted.add(scl);
			added.add(scl);
		}
		return sorted;
	}

	private PackedSentryFile getSentryByName(String name) {
		for (PackedSentryFile scl : sorting) {
			if (scl.getName().equalsIgnoreCase(name) && !added.contains(scl))
				return scl;
		}
		return null;
	}

	private List<String> getSentrysFilenames() {
		List<String> names = new ArrayList<>();
		for (PackedSentryFile scl : sorting)
			names.add(scl.getFilename().toLowerCase());
		return names;
	}

	private List<PackedSentryFile> sortByFilename() {
		List<PackedSentryFile> sorted = new ArrayList<>();
		List<String> sortedFilenames = getSentrysFilenames();
		Collections.sort(sortedFilenames);
		for (String name : sortedFilenames) {
			PackedSentryFile scl = getSentryByFilename(name);
			sorted.add(scl);
			added.add(scl);
		}
		return sorted;
	}

	private PackedSentryFile getSentryByFilename(String fname) {
		for (PackedSentryFile scl : sorting) {
			if (scl.getFilename().equalsIgnoreCase(fname) && !added.contains(scl))
				return scl;
		}
		return null;
	}

}
