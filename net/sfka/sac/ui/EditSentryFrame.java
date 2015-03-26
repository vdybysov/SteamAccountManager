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

import net.sfka.sac.main.SteamAccountManager;
import net.sfka.sac.sentryfiles.PackedSentryFile;
import net.sfka.sac.ui.components.Dragger;
import net.sfka.sac.ui.components.ImageButton;
import net.sfka.sac.ui.components.MinimalisticButton;

public class EditSentryFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField sentryNameTextField;
	private Dragger dragger;

	/**
	 * Create the dialog.
	 */
	public EditSentryFrame(final PackedSentryFile sentry, final SteamAccountManager manager) {
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

		dragger = new Dragger(this, "Rename sentry " + sentry.getFilename(), Colors.DEFAULT_TEXT, Colors.DEFAULT_GRAY_BACKGROUND, Colors.DEFAULT_GRAY_BACKGROUND, new Font("Segoe UI Light", Font.PLAIN, 16));
		dragger.setBounds(0, 0, 356, 24);
		dragPanel.add(dragger);

		JLabel accNameLabel = new JLabel("Sentry name:");
		accNameLabel.setForeground(Colors.DEFAULT_TEXT);
		accNameLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		accNameLabel.setBounds(10, 35, 88, 25);
		getContentPane().add(accNameLabel);

		sentryNameTextField = new JTextField();
		sentryNameTextField.setBounds(108, 35, 150, 25);
		getContentPane().add(sentryNameTextField);
		sentryNameTextField.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		sentryNameTextField.setColumns(10);
		sentryNameTextField.setBorder(new EmptyBorder(sentryNameTextField.getInsets()));
		sentryNameTextField.setBackground(Colors.DEFAULT_GRAY_BACKGROUND);
		sentryNameTextField.setForeground(Colors.DEFAULT_TEXT);
		sentryNameTextField.setSelectedTextColor(Colors.DEFAULT_TEXT);
		sentryNameTextField.setSelectionColor(Colors.ACTIVE);
		sentryNameTextField.setCaretColor(Colors.DEFAULT_TEXT);
		sentryNameTextField.setText(sentry.getName());

		MinimalisticButton saveButton = new MinimalisticButton("Save", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String newName = sentryNameTextField.getText();
				if (newName.length() > 0) {
					sentry.setName(newName);
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
