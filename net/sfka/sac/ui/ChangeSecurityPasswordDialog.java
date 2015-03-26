package net.sfka.sac.ui;

import net.sfka.sac.main.SteamAccountManager;

public class ChangeSecurityPasswordDialog extends SetSecurityPasswordDialog {

	private static final long serialVersionUID = 1L;

	public ChangeSecurityPasswordDialog(SteamAccountManager manager) {
		super(manager);
		titleLabel2.setText("");
		titleLabel1.setText("Enter new security password:");
	}

	@Override
	protected void accept() {
		if (savePassword()) {
			setVisible(false);
		}
	}

}
