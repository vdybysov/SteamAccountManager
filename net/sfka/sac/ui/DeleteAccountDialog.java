package net.sfka.sac.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.sfka.sac.accounts.SteamAccount;
import net.sfka.sac.main.SteamAccountManager;
import net.sfka.sac.ui.components.MinimalisticButton;

public class DeleteAccountDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	protected List<?> toDelete;
	protected SteamAccountManager manager;
	protected JLabel warningLabel;

	/**
	 * Create the dialog.
	 */
	public DeleteAccountDialog(List<?> toDelete, SteamAccountManager manager) {
		this.toDelete = toDelete;
		this.manager = manager;
		setUndecorated(true);
		setSize(318, 118);
		Dimension screen = getToolkit().getScreenSize();
		setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Colors.DARK_BACKGROUND);

		JLabel lblHowDoYou = new JLabel("Are you sure?");
		lblHowDoYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblHowDoYou.setForeground(Color.WHITE);
		lblHowDoYou.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		lblHowDoYou.setBounds(10, 11, 300, 25);
		getContentPane().add(lblHowDoYou);

		MinimalisticButton mnmlstcbtnCreateNew = new MinimalisticButton("Yes", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		mnmlstcbtnCreateNew.setText("Delete");
		mnmlstcbtnCreateNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accept();
			}
		});
		mnmlstcbtnCreateNew.setForeground(Color.WHITE);
		mnmlstcbtnCreateNew.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		mnmlstcbtnCreateNew.setBounds(52, 72, 82, 24);
		getContentPane().add(mnmlstcbtnCreateNew);

		MinimalisticButton mnmlstcbtnAddFromFile = new MinimalisticButton("No", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		mnmlstcbtnAddFromFile.setText("Cancel");
		mnmlstcbtnAddFromFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		mnmlstcbtnAddFromFile.setForeground(Color.WHITE);
		mnmlstcbtnAddFromFile.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		mnmlstcbtnAddFromFile.setBounds(168, 72, 82, 24);
		getContentPane().add(mnmlstcbtnAddFromFile);

		warningLabel = new JLabel("Account data can not be restored!");
		warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		warningLabel.setForeground(Color.WHITE);
		warningLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		warningLabel.setBounds(10, 36, 300, 25);
		getContentPane().add(warningLabel);
	}

	protected void accept() {
		for(Object o : toDelete) {
			if(o instanceof SteamAccount) {
				manager.deleteAccount((SteamAccount) o);
			}
		}
		manager.refreshUI();
		setVisible(false);
	}
}
