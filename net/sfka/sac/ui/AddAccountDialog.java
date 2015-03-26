package net.sfka.sac.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;

import net.sfka.sac.accounts.SteamAccount;
import net.sfka.sac.main.SteamAccountManager;
import net.sfka.sac.ui.components.MinimalisticButton;

import org.apache.commons.lang3.SerializationUtils;

public class AddAccountDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the dialog.
	 */
	public AddAccountDialog(final SteamAccountManager manager) {
		setUndecorated(true);
		setSize(411, 84);
		Dimension screen = getToolkit().getScreenSize();
		setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Colors.DARK_BACKGROUND);

		JLabel lblHowDoYou = new JLabel("How do you want to add the account?");
		lblHowDoYou.setForeground(Color.WHITE);
		lblHowDoYou.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		lblHowDoYou.setBounds(10, 11, 289, 25);
		getContentPane().add(lblHowDoYou);

		MinimalisticButton mnmlstcbtnCreateNew = new MinimalisticButton("Add account", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		mnmlstcbtnCreateNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new CreateAccountFrame(manager).setVisible(true);
			}
		});
		mnmlstcbtnCreateNew.setText("Create new");
		mnmlstcbtnCreateNew.setForeground(Color.WHITE);
		mnmlstcbtnCreateNew.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		mnmlstcbtnCreateNew.setBounds(10, 47, 113, 24);
		getContentPane().add(mnmlstcbtnCreateNew);

		MinimalisticButton mnmlstcbtnAddFromFile = new MinimalisticButton("Add account", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		mnmlstcbtnAddFromFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (SteamAccount sa : openSteamAccountOpenFromFileDialog()) {
					manager.addAccount(sa);
				}
				setVisible(false);
			}
		});
		mnmlstcbtnAddFromFile.setText("From file");
		mnmlstcbtnAddFromFile.setForeground(Color.WHITE);
		mnmlstcbtnAddFromFile.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		mnmlstcbtnAddFromFile.setBounds(133, 47, 99, 24);
		getContentPane().add(mnmlstcbtnAddFromFile);

		MinimalisticButton mnmlstcbtnImportFromSteam = new MinimalisticButton("Add account", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		mnmlstcbtnImportFromSteam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ImportFromSteamFrame(manager).setVisible(true);
				setVisible(false);
			}
		});
		mnmlstcbtnImportFromSteam.setText("Import from Steam");
		mnmlstcbtnImportFromSteam.setForeground(Color.WHITE);
		mnmlstcbtnImportFromSteam.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		mnmlstcbtnImportFromSteam.setBounds(242, 47, 158, 24);
		getContentPane().add(mnmlstcbtnImportFromSteam);
	}

	private List<SteamAccount> openSteamAccountOpenFromFileDialog() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		chooser.setDialogTitle("Open SteamAccount file");
		chooser.setMultiSelectionEnabled(true);
		chooser.setAcceptAllFileFilterUsed(true);
		chooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "";
			}

			@Override
			public boolean accept(File f) {
				if(f.isDirectory())
					return true;
				String ext = f.getName();
				String[] extSplit = ext.split("\\.");
				if (extSplit.length > 0) {
					ext = extSplit[extSplit.length - 1];
					if (ext.equalsIgnoreCase("sa")) {
						return true;
					}
				}
				return false;
			}
		});
		chooser.showOpenDialog(this);
		List<SteamAccount> parsed = new ArrayList<>();
		for (File f : chooser.getSelectedFiles()) {
			try {
				SteamAccount sa = SerializationUtils.deserialize(Files.readAllBytes(f.toPath()));
				parsed.add(sa);
			} catch (Exception e) {
				setVisible(false);
				e.printStackTrace();
			}
		}
		return parsed;
	}
}
