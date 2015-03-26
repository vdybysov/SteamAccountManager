package net.sfka.sac.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;

import net.sfka.sac.main.SteamAccountManager;
import net.sfka.sac.ui.components.MinimalisticButton;
import net.sfka.sac.utils.ProcessesUtils;

public class KillSteamDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Create the dialog.
	 */
	public KillSteamDialog(final SteamAccountManager manager) {
		setUndecorated(true);
		setSize(318, 84);
		Dimension screen = getToolkit().getScreenSize();
		setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Colors.DARK_BACKGROUND);
		
		JLabel lblHowDoYou = new JLabel("Steam is working. Do you want to kill it?");
		lblHowDoYou.setForeground(Color.WHITE);
		lblHowDoYou.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		lblHowDoYou.setBounds(10, 11, 300, 25);
		getContentPane().add(lblHowDoYou);
		
		MinimalisticButton mnmlstcbtnCreateNew = new MinimalisticButton("Yes", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		mnmlstcbtnCreateNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProcessesUtils.killSteam();
				manager.launchSteam();
				setVisible(false);
			}
		});
		mnmlstcbtnCreateNew.setForeground(Color.WHITE);
		mnmlstcbtnCreateNew.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		mnmlstcbtnCreateNew.setBounds(69, 47, 66, 24);
		getContentPane().add(mnmlstcbtnCreateNew);
		
		MinimalisticButton mnmlstcbtnAddFromFile = new MinimalisticButton("No", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		mnmlstcbtnAddFromFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		mnmlstcbtnAddFromFile.setForeground(Color.WHITE);
		mnmlstcbtnAddFromFile.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		mnmlstcbtnAddFromFile.setBounds(169, 47, 66, 24);
		getContentPane().add(mnmlstcbtnAddFromFile);
	}
}
