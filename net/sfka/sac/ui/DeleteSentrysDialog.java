package net.sfka.sac.ui;

import java.util.List;

import net.sfka.sac.main.SteamAccountManager;
import net.sfka.sac.sentryfiles.PackedSentryFile;

public class DeleteSentrysDialog extends DeleteAccountDialog {

	private static final long serialVersionUID = 1L;

	public DeleteSentrysDialog(List<?> toDelete, SteamAccountManager manager) {
		super(toDelete, manager);
		warningLabel.setText("Sentry Files can not be restored!");
	}
	
	protected void accept() {
		for(Object o : toDelete) {
			if(o instanceof PackedSentryFile) {
				manager.deleteSentryFile((PackedSentryFile) o);
			}
		}
		manager.refreshUI();
		setVisible(false);
	}

}
