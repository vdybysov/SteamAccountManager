package net.sfka.sac.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import net.sfka.sac.main.SteamAccountManager;
import net.sfka.sac.ui.components.MinimalisticButton;

public class SetSecurityPasswordDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPasswordField passwordField, confirmFasswordField;
	private JLabel messageLabel;
	private MinimalisticButton doneButton;
	protected JLabel titleLabel1, titleLabel2;
	protected SteamAccountManager manager;

	/**
	 * Create the dialog.
	 */
	public SetSecurityPasswordDialog(final SteamAccountManager manager) {
		this.manager = manager;
		setUndecorated(true);
		setSize(302, 187);
		Dimension screen = getToolkit().getScreenSize();
		setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Colors.DARK_BACKGROUND);

		titleLabel1 = new JLabel("To protect your account data");
		titleLabel1.setForeground(Color.WHITE);
		titleLabel1.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		titleLabel1.setBounds(10, 11, 282, 25);
		getContentPane().add(titleLabel1);

		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					doneButton.doClick();
				}
			}
		});
		passwordField.setBounds(10, 72, 283, 24);
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
				accept();
			}
		});
		doneButton.setForeground(Color.WHITE);
		doneButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		doneButton.setBounds(10, 146, 76, 24);
		getContentPane().add(doneButton);

		titleLabel2 = new JLabel("you should set the security password:");
		titleLabel2.setForeground(Color.WHITE);
		titleLabel2.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		titleLabel2.setBounds(10, 36, 283, 25);
		getContentPane().add(titleLabel2);

		confirmFasswordField = new JPasswordField();
		confirmFasswordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == 10) {
					doneButton.doClick();
				}
			}
		});
		confirmFasswordField.setSelectionColor(new Color(225, 140, 50));
		confirmFasswordField.setSelectedTextColor(Color.WHITE);
		confirmFasswordField.setForeground(Color.WHITE);
		confirmFasswordField.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		confirmFasswordField.setColumns(10);
		confirmFasswordField.setCaretColor(Color.WHITE);
		confirmFasswordField.setBorder(new EmptyBorder(passwordField.getInsets()));
		confirmFasswordField.setBackground(new Color(50, 50, 50));
		confirmFasswordField.setBounds(10, 107, 283, 24);
		getContentPane().add(confirmFasswordField);

		messageLabel = new JLabel("");
		messageLabel.setForeground(Color.RED);
		messageLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		messageLabel.setBounds(96, 146, 196, 24);
		getContentPane().add(messageLabel);
	}

	protected void accept() {
		if (savePassword()) {
			manager.initUI();
			setVisible(false);
		}
	}

	protected boolean savePassword() {
		messageLabel.setVisible(false);
		if (passwordField.getPassword().length < 4) {
			messageLabel.setText("Password is too short");
			messageLabel.setVisible(true);
			return false;
		}
		if (passwordField.getPassword().length == confirmFasswordField.getPassword().length) {
			for (int i = 0; i < passwordField.getPassword().length; i++) {
				if (passwordField.getPassword()[i] != confirmFasswordField.getPassword()[i]) {
					messageLabel.setText("Passwords don't match");
					messageLabel.setVisible(true);
					return false;
				}
			}
		} else {
			messageLabel.setText("Passwords don't match");
			messageLabel.setVisible(true);
			return false;
		}
		manager.getData().setSecurityPassword(passwordField.getPassword());
		manager.saveData();
		return true;
	}

	class FolderFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return new File(dir, name).isDirectory();
		}
	}
}
