package net.sfka.sac.sentryfiles;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class SentryFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if(f.length() == 2048 || f.isDirectory()) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "Sentry File";
	}

}
