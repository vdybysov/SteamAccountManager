package net.sfka.sac.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import net.sfka.sac.accounts.SteamAccount;

import org.apache.commons.lang3.SerializationUtils;

public class Main {

	public static final String VERSION = "0.2 Beta";
	public static final long DOUBLE_CLICK_DELAY = 200;

	public static void main(String args[]) {
		SteamAccountManager manager = new SteamAccountManager();
		manager.refreshUI();
		if (args.length > 0) {
			File toOpen = new File(args[0]);
			if (toOpen.exists()) {
				try {
					SteamAccount sa = SerializationUtils.deserialize(Files.readAllBytes(toOpen.toPath()));
					if (sa != null) {
						manager.addAccount(sa);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
