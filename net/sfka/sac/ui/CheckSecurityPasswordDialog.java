package net.sfka.sac.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import net.sfka.sac.main.SteamAccountManager;
import net.sfka.sac.ui.components.MinimalisticButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CheckSecurityPasswordDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPasswordField passwordField;
	private JLabel messageLabel;
	private MinimalisticButton doneButton;

	/**
	 * Create the dialog.
	 */
	public CheckSecurityPasswordDialog(final SteamAccountManager manager) {
		setUndecorated(true);
		setSize(302, 118);
		Dimension screen = getToolkit().getScreenSize();
		setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Colors.DARK_BACKGROUND);

		JLabel lblHowDoYou = new JLabel("Please enter your security password:");
		lblHowDoYou.setForeground(Color.WHITE);
		lblHowDoYou.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		lblHowDoYou.setBounds(10, 11, 512, 25);
		getContentPane().add(lblHowDoYou);

		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					doneButton.doClick();
				}
			}
		});
		passwordField.setBounds(10, 47, 283, 24);
		getContentPane().add(passwordField);
		passwordField.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		passwordField.setColumns(10);
		passwordField.setBorder(new EmptyBorder(passwordField.getInsets()));
		passwordField.setBackground(Colors.DEFAULT_GRAY_BACKGROUND);
		passwordField.setForeground(Colors.DEFAULT_TEXT);
		passwordField.setSelectedTextColor(Colors.DEFAULT_TEXT);
		passwordField.setSelectionColor(Colors.ACTIVE);
		passwordField.setCaretColor(Colors.DEFAULT_TEXT);

		doneButton = new MinimalisticButton("Done", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (passwordField.getPassword().length == manager.getData().getSecurityPassword().length) {
					for (int i = 0; i < passwordField.getPassword().length; i++) {
						if (passwordField.getPassword()[i] != manager.getData().getSecurityPassword()[i]) {
							messageLabel.setText("Wrong password!");
							messageLabel.setVisible(true);
							return;
						}
					}
				} else {
					messageLabel.setText("Wrong password!");
					messageLabel.setVisible(true);
					return;
				}
				setVisible(false);
				manager.initUI();
			}
		});
		doneButton.setForeground(Color.WHITE);
		doneButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		doneButton.setBounds(10, 82, 76, 24);
		getContentPane().add(doneButton);

		messageLabel = new JLabel("");
		messageLabel.setForeground(Color.RED);
		messageLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		messageLabel.setBounds(164, 82, 129, 24);
		getContentPane().add(messageLabel);

		MinimalisticButton mnmlstcbtnCancel = new MinimalisticButton("Done", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		mnmlstcbtnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnmlstcbtnCancel.setText("Exit");
		mnmlstcbtnCancel.setForeground(Color.WHITE);
		mnmlstcbtnCancel.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		mnmlstcbtnCancel.setBounds(96, 82, 58, 24);
		getContentPane().add(mnmlstcbtnCancel);
	}

	class FolderFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return new File(dir, name).isDirectory();
		}
	}
}
