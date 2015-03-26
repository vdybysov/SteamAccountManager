package net.sfka.sac.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.sfka.sac.main.SteamAccountManager;
import net.sfka.sac.ui.components.MinimalisticButton;

public class SpecifySteamDirDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTextField steamDirTextField;

	/**
	 * Create the dialog.
	 */
	public SpecifySteamDirDialog(final SteamAccountManager manager) {
		setUndecorated(true);
		setSize(411, 120);
		Dimension screen = getToolkit().getScreenSize();
		setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Colors.DARK_BACKGROUND);

		JLabel lblHowDoYou = new JLabel("Specify the Steam directory please:");
		lblHowDoYou.setForeground(Color.WHITE);
		lblHowDoYou.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		lblHowDoYou.setBounds(10, 11, 262, 25);
		getContentPane().add(lblHowDoYou);

		steamDirTextField = new JTextField();
		steamDirTextField.setBounds(10, 48, 283, 24);
		getContentPane().add(steamDirTextField);
		steamDirTextField.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		steamDirTextField.setColumns(10);
		steamDirTextField.setBorder(new EmptyBorder(steamDirTextField.getInsets()));
		steamDirTextField.setBackground(Colors.DEFAULT_GRAY_BACKGROUND);
		steamDirTextField.setForeground(Colors.DEFAULT_TEXT);
		steamDirTextField.setSelectedTextColor(Colors.DEFAULT_TEXT);
		steamDirTextField.setSelectionColor(Colors.ACTIVE);
		steamDirTextField.setCaretColor(Colors.DEFAULT_TEXT);

		MinimalisticButton browseButton = new MinimalisticButton("Done", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File steamDir = openSteamDirChoosingDialog();
				if (steamDir != null) {
					steamDirTextField.setText(steamDir.getAbsolutePath());
				}
			}
		});
		browseButton.setText("Browse...");
		browseButton.setForeground(Color.WHITE);
		browseButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		browseButton.setBounds(303, 48, 98, 24);
		getContentPane().add(browseButton);

		MinimalisticButton mnmlstcbtnDone = new MinimalisticButton("Done", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		mnmlstcbtnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File steamDir = new File(steamDirTextField.getText());
				if (steamDir.exists() && steamDir.isDirectory()) {
					manager.getData().setSteamdir(steamDir);
					manager.initPasswordCheckUI();
					manager.saveData();
					dispose();
				}
			}
		});
		mnmlstcbtnDone.setForeground(Color.WHITE);
		mnmlstcbtnDone.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		mnmlstcbtnDone.setBounds(10, 85, 76, 24);
		getContentPane().add(mnmlstcbtnDone);
	}

	private File openSteamDirChoosingDialog() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("select folder");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.showOpenDialog(this);
		return chooser.getSelectedFile();
	}

	class FolderFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return new File(dir, name).isDirectory();
		}
	}
}
