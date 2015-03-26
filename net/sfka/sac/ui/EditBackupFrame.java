package net.sfka.sac.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.sfka.sac.backups.SteamBackup;
import net.sfka.sac.main.SteamAccountManager;
import net.sfka.sac.ui.components.Dragger;
import net.sfka.sac.ui.components.ImageButton;
import net.sfka.sac.ui.components.MinimalisticButton;

public class EditBackupFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField backupNameTextField;
	private Dragger dragger;

	/**
	 * Create the dialog.
	 */
	public EditBackupFrame(final SteamBackup backup, final SteamAccountManager manager) {
		setUndecorated(true);
		setSize(356, 70);
		Dimension screen = getToolkit().getScreenSize();
		setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Colors.SHADOW_BACKGROUND);

		JPanel dragPanel = new JPanel();
		dragPanel.setBounds(0, 0, 356, 24);
		getContentPane().add(dragPanel);
		dragPanel.setLayout(null);
		dragPanel.setBackground(Colors.DEFAULT_GRAY_BACKGROUND);

		ImageButton closeButton = new ImageButton("close_button");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		closeButton.setBounds(332, 0, 24, 24);
		dragPanel.add(closeButton);

		dragger = new Dragger(this, "Rename backup", Colors.DEFAULT_TEXT, Colors.DEFAULT_GRAY_BACKGROUND, Colors.DEFAULT_GRAY_BACKGROUND, new Font("Segoe UI Light", Font.PLAIN, 16));
		dragger.setBounds(0, 0, 356, 24);
		dragPanel.add(dragger);

		JLabel accNameLabel = new JLabel("Backup name:");
		accNameLabel.setForeground(Colors.DEFAULT_TEXT);
		accNameLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		accNameLabel.setBounds(10, 35, 95, 25);
		getContentPane().add(accNameLabel);

		backupNameTextField = new JTextField();
		backupNameTextField.setBounds(115, 35, 143, 25);
		getContentPane().add(backupNameTextField);
		backupNameTextField.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		backupNameTextField.setColumns(10);
		backupNameTextField.setBorder(new EmptyBorder(backupNameTextField.getInsets()));
		backupNameTextField.setBackground(Colors.DEFAULT_GRAY_BACKGROUND);
		backupNameTextField.setForeground(Colors.DEFAULT_TEXT);
		backupNameTextField.setSelectedTextColor(Colors.DEFAULT_TEXT);
		backupNameTextField.setSelectionColor(Colors.ACTIVE);
		backupNameTextField.setCaretColor(Colors.DEFAULT_TEXT);
		backupNameTextField.setText(backup.getName());
		
		MinimalisticButton saveButton = new MinimalisticButton("Save", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String newName = backupNameTextField.getText();
				if (newName.length() > 0) {
					backup.setName(newName);
					manager.refreshUI();
					setVisible(false);
				}
			}
		});
		saveButton.setForeground(Color.WHITE);
		saveButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		saveButton.setBounds(268, 35, 78, 24);
		getContentPane().add(saveButton);
	}

}
