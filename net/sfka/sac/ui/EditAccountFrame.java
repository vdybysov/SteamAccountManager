package net.sfka.sac.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sfka.sac.accounts.SteamAccount;
import net.sfka.sac.main.SteamAccountManager;
import net.sfka.sac.sentryfiles.PackedSentryFile;

public class EditAccountFrame extends CreateAccountFrame {

	private static final long serialVersionUID = 1L;
	private SteamAccount account;
	/**
	 * Create the dialog.
	 */
	public EditAccountFrame(SteamAccount account, final SteamAccountManager manager) {
		super(manager);
		this.account = account;
		setTitle("Edit " + account.getLogin());
		accNameTextField.setText(account.getLogin());
		passwordField.setText(account.getDecodedPassword());
		configFileTextField.setText("<config.vdf>");
		descriptionTextField.setText(account.getDescription());
		profileIDTextField.setText(account.getProfileID());
		setSentrysList(account.getSentryFiles());
	}

	@Override
	protected void saveAccount() {
		messageLabel.setVisible(false);
		String accName = accNameTextField.getText();
		if (accName.length() == 0) {
			messageLabel.setText("Specify the account name!");
			messageLabel.setVisible(true);
			return;
		}
		String pass = String.valueOf(passwordField.getPassword());
		if (pass.length() == 0) {
			messageLabel.setText("Specify the password!");
			messageLabel.setVisible(true);
			return;
		}
		if (!configFileTextField.getText().equals("<config.vdf>")) {
			String config = "";
			File cfgFile = new File(configFileTextField.getText());
			if (cfgFile.exists()) {
				try {
					BufferedReader r = new BufferedReader(new FileReader(cfgFile));
					if ((config = r.readLine()) != null) {
						String line;
						while ((line = r.readLine()) != null) {
							config += "\n" + line;
						}
					}
					r.close();
					account.setConfig(config);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		List<PackedSentryFile> accSentrys = new ArrayList<>();
		for (int r = 0; r < sentrysTable.getRowCount(); r++) {
			PackedSentryFile psf = manager.getSentryFileByFilename(sentrysTable.getValueAt(r, 1).toString());
			if (psf != null) {
				accSentrys.add(psf);
			}
		}
		account.setLogin(accName);
		account.setRawPassword(pass);
		account.setDescription(descriptionTextField.getText());
		account.setSentryFiles(accSentrys);
		manager.saveAccounts();
		manager.refreshUI();
		dispose();
	}

	protected void openSentrysSelectionFrame() {
		new SelectSentrysFrame(this, manager).setVisible(true);
	}
}
