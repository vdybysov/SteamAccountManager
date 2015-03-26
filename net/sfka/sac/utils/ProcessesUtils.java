package net.sfka.sac.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessesUtils {
	
	public static void launchSteam(String exe, String login, String password) {
		List<String> launchArgs = new ArrayList<>();
		launchArgs.add(exe);
		launchArgs.add("-login");
		launchArgs.add(login);
		launchArgs.add(password);
		try {
			new ProcessBuilder(launchArgs).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isSteamWorking() {
		for(String s : listProcesses()) {
			if(s.toLowerCase().contains("steam.exe")) {
				return true;
			}
		}
		return false;
	}
	
	public static List<String> listProcesses() {
		List<String> processes = new ArrayList<>();
		try {
			Process tasklistProcess = Runtime.getRuntime().exec("tasklist");
			BufferedReader r = new BufferedReader(new InputStreamReader(tasklistProcess.getInputStream()));
			String p;
			while((p = r.readLine()) != null) {
				processes.add(p);
			}
			r.close();
			tasklistProcess.destroy();
			return processes;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void killSteam() {
		try {
			Runtime.getRuntime().exec("taskkill /f /IM Steam.exe");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
