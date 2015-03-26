package net.sfka.sac.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.sfka.sac.accounts.SteamAccount;
import net.sfka.sac.backups.SteamBackup;
import net.sfka.sac.main.Main;
import net.sfka.sac.main.SteamAccountManager;
import net.sfka.sac.sentryfiles.PackedSentryFile;
import net.sfka.sac.sentryfiles.SentryFileFilter;
import net.sfka.sac.ui.components.Dragger;
import net.sfka.sac.ui.components.ImageButton;
import net.sfka.sac.ui.components.LabelButton;
import net.sfka.sac.ui.components.MinimalisticButton;
import net.sfka.sac.ui.components.MinimalisticScrollbarUI;
import net.sfka.sac.ui.components.MinimalisticTable;
import net.sfka.sac.ui.components.MinimalisticTableCellLabelRenderer;
import net.sfka.sac.ui.components.MinimalisticTableHeaderRenderer;
import net.sfka.sac.utils.ImageUtils;
import net.sfka.sac.utils.ProcessesUtils;

public class MainWindow {

	private JFrame frame;
	private SteamAccountManager manager;
	private JPanel accountsPanel, settingsPanel, sentrysPanel, aboutPanel,
			backupPanel;
	private LabelButton accountsButton, sentrysButton, settingsButton,
			helpMenuButton, backupMenuButton;
	private MinimalisticTable accountsTable, sentrysTable, backupsTable;
	private Container accountsTableContainer, sentrysTableContainer,
			backupsTableContainer;
	private JScrollPane accountsTableScrollPane, sentrysTableScrollPane,
			backupsTableScrollPane;
	private Font tablesFont = new Font("Segoe UI Light", Font.PLAIN, 16);
	private JDialog addAccountDialog;
	private JLabel selectedAccountLabel;
	private boolean waitingForDoubleClickOnAccsTable = false,
			waitingForDoubleClickOnSentrysTable = false,
			waitingForDoubleClickOnBackupsTable = false;
	private MinimalisticButton launchSteamButton;
	private LabelButton showPasswordsButton;
	private boolean showPasswords = false;
	private ImageButton addSentryButton, editSentryButton, deleteSentryButton;
	private JTextField steamDirTextField;
	private ImageButton addBackupButton, deleteBackupButton, editBackupButton,
			extractBackupButton;
	private JLabel backupStatusLabel;
	private JLabel versionLabel;
	private JLabel lblThisIsBeta;
	private JLabel lblThisissandfieldgmailcom;
	private JLabel lblQiwiWallet;
	private JLabel label_2;
	private JSeparator separator_4;
	private JLabel lblEnjoy;
	private JSeparator separator_5;
	private JLabel lblHelp;
	private JLabel lblHelpHere;
	private JLabel lblSteamCommunity;
	private ImageButton editAccountButton;

	public MainWindow(SteamAccountManager manager) {
		super();
		this.manager = manager;
		addAccountDialog = new AddAccountDialog(manager);
		addAccountDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				manager.exit();
			}
		});
		frame.setSize(600, 400);
		Dimension screen = frame.getToolkit().getScreenSize();
		frame.setLocation(screen.width / 2 - frame.getWidth() / 2, screen.height / 2 - frame.getHeight() / 2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setBackground(Colors.DEFAULT_BACKGROUND);
		frame.setIconImage(ImageUtils.getLocalImage("icon"));
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(null);

		aboutPanel = new JPanel();
		aboutPanel.setLayout(null);
		aboutPanel.setBackground(Colors.DEFAULT_BACKGROUND);
		aboutPanel.setBounds(0, 36, 600, 364);
		frame.getContentPane().add(aboutPanel);

		JLabel lblSteamAccountManager = new JLabel("Steam Account Manager by Sandfield");
		lblSteamAccountManager.setHorizontalAlignment(SwingConstants.LEFT);
		lblSteamAccountManager.setForeground(Color.WHITE);
		lblSteamAccountManager.setFont(new Font("Segoe UI Light", Font.PLAIN, 26));
		lblSteamAccountManager.setBounds(10, 11, 413, 36);
		aboutPanel.add(lblSteamAccountManager);

		JLabel lblToolHelpsYou = new JLabel("Tool helps you to switch between your Steam accounts fast.");
		lblToolHelpsYou.setHorizontalAlignment(SwingConstants.LEFT);
		lblToolHelpsYou.setForeground(Color.WHITE);
		lblToolHelpsYou.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		lblToolHelpsYou.setBounds(10, 70, 564, 27);
		aboutPanel.add(lblToolHelpsYou);

		lblThisIsBeta = new JLabel("This is BETA version of the tool. For any bug reports or suggestions feel free to contact me:");
		lblThisIsBeta.setHorizontalAlignment(SwingConstants.LEFT);
		lblThisIsBeta.setForeground(Color.WHITE);
		lblThisIsBeta.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		lblThisIsBeta.setBounds(10, 108, 580, 27);
		aboutPanel.add(lblThisIsBeta);

		lblThisissandfieldgmailcom = new JLabel("ThisIsSandfield@gmail.com");
		lblThisissandfieldgmailcom.setHorizontalAlignment(SwingConstants.LEFT);
		lblThisissandfieldgmailcom.setForeground(Colors.ACTIVE);
		lblThisissandfieldgmailcom.setFont(new Font("Segoe UI Light", Font.ITALIC, 18));
		lblThisissandfieldgmailcom.setBounds(10, 146, 207, 27);
		aboutPanel.add(lblThisissandfieldgmailcom);

		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(new Color(90, 90, 90));
		separator_2.setBackground(new Color(25, 25, 25));
		separator_2.setBounds(10, 58, 580, 1);
		aboutPanel.add(separator_2);

		JLabel lblDonate = new JLabel("Donate:");
		lblDonate.setHorizontalAlignment(SwingConstants.LEFT);
		lblDonate.setForeground(Color.WHITE);
		lblDonate.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		lblDonate.setBounds(10, 196, 65, 27);
		aboutPanel.add(lblDonate);

		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(new Color(90, 90, 90));
		separator_3.setBackground(new Color(25, 25, 25));
		separator_3.setBounds(10, 184, 580, 1);
		aboutPanel.add(separator_3);

		JLabel lblWebmoney = new JLabel("WebMoney:");
		lblWebmoney.setHorizontalAlignment(SwingConstants.LEFT);
		lblWebmoney.setForeground(Color.WHITE);
		lblWebmoney.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblWebmoney.setBounds(10, 234, 81, 22);
		aboutPanel.add(lblWebmoney);

		JLabel lblZ = new JLabel("Z281688817148");
		lblZ.setHorizontalAlignment(SwingConstants.LEFT);
		lblZ.setForeground(Colors.ACTIVE);
		lblZ.setFont(new Font("Segoe UI Light", Font.ITALIC, 16));
		lblZ.setBounds(101, 234, 100, 22);
		aboutPanel.add(lblZ);

		lblQiwiWallet = new JLabel("QIWI Wallet:");
		lblQiwiWallet.setHorizontalAlignment(SwingConstants.LEFT);
		lblQiwiWallet.setForeground(Color.WHITE);
		lblQiwiWallet.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblQiwiWallet.setBounds(10, 267, 83, 22);
		aboutPanel.add(lblQiwiWallet);

		label_2 = new JLabel("79532849105");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setForeground(new Color(225, 140, 50));
		label_2.setFont(new Font("Segoe UI Light", Font.ITALIC, 16));
		label_2.setBounds(101, 267, 87, 22);
		aboutPanel.add(label_2);

		separator_4 = new JSeparator();
		separator_4.setForeground(new Color(90, 90, 90));
		separator_4.setBackground(new Color(25, 25, 25));
		separator_4.setBounds(10, 300, 580, 1);
		aboutPanel.add(separator_4);

		lblEnjoy = new JLabel("Enjoy :)");
		lblEnjoy.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnjoy.setForeground(new Color(225, 140, 50));
		lblEnjoy.setFont(new Font("Segoe UI Light", Font.BOLD, 20));
		lblEnjoy.setBounds(10, 312, 81, 41);
		aboutPanel.add(lblEnjoy);

		separator_5 = new JSeparator();
		separator_5.setOrientation(SwingConstants.VERTICAL);
		separator_5.setForeground(new Color(90, 90, 90));
		separator_5.setBackground(new Color(25, 25, 25));
		separator_5.setBounds(215, 196, 2, 93);
		aboutPanel.add(separator_5);

		lblHelp = new JLabel("Help:");
		lblHelp.setHorizontalAlignment(SwingConstants.LEFT);
		lblHelp.setForeground(Color.WHITE);
		lblHelp.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		lblHelp.setBounds(227, 196, 43, 27);
		aboutPanel.add(lblHelp);

		lblHelpHere = new JLabel("Here");
		lblHelpHere.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblHelpHere.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				manager.openURL("http://pastebin.com/v6wZXDj1");
			}
		});
		lblHelpHere.setHorizontalAlignment(SwingConstants.LEFT);
		lblHelpHere.setForeground(Colors.ACTIVE);
		lblHelpHere.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		lblHelpHere.setBounds(227, 234, 43, 22);
		aboutPanel.add(lblHelpHere);

		lblSteamCommunity = new JLabel("steamcommunity.com/id/SFElite");
		lblSteamCommunity.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblSteamCommunity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				manager.openURL("http://steamcommunity.com/id/SFElite");
			}
		});
		lblSteamCommunity.setHorizontalAlignment(SwingConstants.LEFT);
		lblSteamCommunity.setForeground(new Color(225, 140, 50));
		lblSteamCommunity.setFont(new Font("Segoe UI Light", Font.ITALIC, 18));
		lblSteamCommunity.setBounds(227, 146, 243, 27);
		aboutPanel.add(lblSteamCommunity);

		JPanel dragPanel = new JPanel();
		dragPanel.setBounds(0, 0, 600, 36);
		frame.getContentPane().add(dragPanel);
		dragPanel.setLayout(null);
		dragPanel.setBackground(Colors.DARK_BACKGROUND);

		ImageButton closeButton = new ImageButton("exit_button");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				manager.exit();
			}
		});
		closeButton.setBounds(542, 0, 48, 20);
		dragPanel.add(closeButton);
		Font menuFont = new Font("Segoe UI Light", Font.PLAIN, 36);
		accountsButton = new LabelButton("Accounts", menuFont, null, Colors.INACTIVE_TEXT, Colors.DEFAULT_TEXT, Colors.DEFAULT_TEXT);
		accountsButton.setSelected(true);
		accountsButton.setBounds(10, 2, 94, 34);
		dragPanel.add(accountsButton);
		accountsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchMenu(0);
			}
		});
		accountsButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 22));

		sentrysButton = new LabelButton("Accounts", new Font("Segoe UI Light", Font.PLAIN, 36), (Color) null, Colors.INACTIVE_TEXT, Colors.DEFAULT_TEXT, Colors.DEFAULT_TEXT);
		sentrysButton.setBounds(114, 2, 108, 34);
		dragPanel.add(sentrysButton);
		sentrysButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchMenu(1);
			}
		});
		sentrysButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 22));
		sentrysButton.setText("Sentry Files");

		settingsButton = new LabelButton("Accounts", new Font("Segoe UI Light", Font.PLAIN, 36), null, Colors.INACTIVE_TEXT, Colors.DEFAULT_TEXT, Colors.DEFAULT_TEXT);
		settingsButton.setBounds(328, 2, 79, 34);
		dragPanel.add(settingsButton);
		settingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchMenu(3);
			}
		});
		settingsButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 22));
		settingsButton.setText("Settings");

		helpMenuButton = new LabelButton("Accounts", new Font("Segoe UI Light", Font.PLAIN, 36), (Color) null, new Color(150, 150, 150), Color.WHITE, Color.WHITE);
		helpMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switchMenu(4);
			}
		});
		helpMenuButton.setText("About");
		helpMenuButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 22));
		helpMenuButton.setBounds(425, 2, 65, 34);
		dragPanel.add(helpMenuButton);

		backupMenuButton = new LabelButton("Backup", new Font("Segoe UI Light", Font.PLAIN, 36), (Color) null, new Color(150, 150, 150), Color.WHITE, Color.WHITE);
		backupMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchMenu(2);
			}
		});
		backupMenuButton.setSelected(false);
		backupMenuButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 22));
		backupMenuButton.setBounds(238, 2, 73, 34);
		dragPanel.add(backupMenuButton);

		versionLabel = new JLabel("");
		versionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		versionLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		versionLabel.setBounds(542, 22, 48, 14);
		versionLabel.setForeground(Colors.DEFAULT_TEXT);
		versionLabel.setText("v" + Main.VERSION);
		dragPanel.add(versionLabel);

		Dragger dragger = new Dragger(frame, "", Colors.DEFAULT_TEXT, Colors.DARK_BACKGROUND, Colors.DEFAULT_GRAY_BACKGROUND, new Font("Segoe UI Light", Font.PLAIN, 22));
		dragger.setBounds(0, 0, 600, 36);
		dragPanel.add(dragger);

		accountsPanel = new JPanel();
		accountsPanel.setBounds(0, 36, 600, 364);
		accountsPanel.setBackground(Colors.DEFAULT_BACKGROUND);
		frame.getContentPane().add(accountsPanel);
		accountsPanel.setLayout(null);

		initAccsTable();

		ImageButton addAccountButton = new ImageButton("add_button");
		addAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAccountDialog.setVisible(true);
			}
		});
		addAccountButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		addAccountButton.setForeground(Colors.DEFAULT_TEXT);
		addAccountButton.setBounds(10, 8, 20, 20);
		accountsPanel.add(addAccountButton);

		editAccountButton = new ImageButton("edit_button");
		editAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (accountsTable.getSelectedRowCount() > 0) {
					SteamAccount sa = manager.getSteamAccountByName(accountsTable.getValueAt(accountsTable.getSelectedRow(), 0).toString());
					if (sa != null) {
						new EditAccountFrame(sa, manager).setVisible(true);
					}
				}
			}
		});
		editAccountButton.setForeground(Color.WHITE);
		editAccountButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		editAccountButton.setBounds(40, 8, 20, 20);
		accountsPanel.add(editAccountButton);

		showPasswordsButton = new LabelButton("Accounts", new Font("Segoe UI Light", Font.PLAIN, 36), (Color) null, new Color(150, 150, 150), Color.WHITE, Color.WHITE);
		showPasswordsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setShowPasswords(showPasswordsButton.isSelected());
			}
		});

		ImageButton deleteAccountButton = new ImageButton("delete_button");
		deleteAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (accountsTable.getSelectedRowCount() > 0) {
					List<SteamAccount> selected = new ArrayList<>();
					for (int r : accountsTable.getSelectedRows()) {
						SteamAccount ca = manager.getSteamAccountByName(accountsTable.getValueAt(r, 0).toString());
						if (ca != null) {
							selected.add(ca);
						}
					}
					new DeleteAccountDialog(selected, manager).setVisible(true);
				}
			}
		});
		deleteAccountButton.setForeground(Color.WHITE);
		deleteAccountButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		deleteAccountButton.setBounds(70, 8, 20, 20);
		accountsPanel.add(deleteAccountButton);
		showPasswordsButton.setText("Show passwords");
		showPasswordsButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		showPasswordsButton.setBounds(96, 14, 104, 20);
		accountsPanel.add(showPasswordsButton);

		launchSteamButton = new MinimalisticButton("Add account", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		launchSteamButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (manager.getSelectedAccount() != null) {
					if (ProcessesUtils.isSteamWorking()) {
						new KillSteamDialog(manager).setVisible(true);
					} else {
						manager.launchSteam();
					}
				}
			}
		});

		JLabel selectedLabel = new JLabel("Selected:");
		selectedLabel.setForeground(Color.WHITE);
		selectedLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		selectedLabel.setBounds(10, 314, 67, 39);
		accountsPanel.add(selectedLabel);

		selectedAccountLabel = new JLabel("sandfield1337");
		selectedAccountLabel.setForeground(Colors.ACTIVE);
		selectedAccountLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		selectedAccountLabel.setBounds(87, 314, 339, 39);
		accountsPanel.add(selectedAccountLabel);
		launchSteamButton.setText("Launch Steam");
		launchSteamButton.setForeground(Color.WHITE);
		launchSteamButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		launchSteamButton.setBounds(436, 314, 154, 39);
		accountsPanel.add(launchSteamButton);

		ImageButton exportAccButton = new ImageButton("export_button");
		exportAccButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (accountsTable.getSelectedRowCount() > 0) {
					SteamAccount sa = manager.getSteamAccountByName(accountsTable.getValueAt(accountsTable.getSelectedRow(), 0).toString());
					if (sa != null) {
						File f = openAccExportDialog(sa);
						if (f != null) {
							try {
								manager.exportAccount(sa, f);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		});
		exportAccButton.setForeground(Color.WHITE);
		exportAccButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		exportAccButton.setBounds(570, 6, 24, 24);
		accountsPanel.add(exportAccButton);

		backupPanel = new JPanel();
		backupPanel.setLayout(null);
		backupPanel.setBackground(Colors.DEFAULT_BACKGROUND);
		backupPanel.setBounds(0, 36, 600, 364);
		frame.getContentPane().add(backupPanel);

		initBackupsTable();

		addBackupButton = new ImageButton("add_button");
		addBackupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					manager.addBackup(new SteamBackup(manager.getData().getSteamdir()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		addBackupButton.setForeground(Color.WHITE);
		addBackupButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		addBackupButton.setBounds(10, 8, 20, 20);
		backupPanel.add(addBackupButton);

		editBackupButton = new ImageButton("edit_button");
		editBackupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SteamBackup b = manager.getSteamBackupByName(backupsTable.getValueAt(backupsTable.getSelectedRow(), 0).toString());
				if (b != null) {
					new EditBackupFrame(b, manager).setVisible(true);
				}
			}
		});
		editBackupButton.setForeground(Color.WHITE);
		editBackupButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		editBackupButton.setBounds(40, 8, 20, 20);
		backupPanel.add(editBackupButton);

		deleteBackupButton = new ImageButton("delete_button");
		deleteBackupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int r : backupsTable.getSelectedRows()) {
					SteamBackup b = manager.getSteamBackupByName(backupsTable.getValueAt(r, 0).toString());
					if (b != null) {
						manager.deleteBackup(b);
					}
				}
				manager.refreshUI();
			}
		});
		deleteBackupButton.setForeground(Color.WHITE);
		deleteBackupButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		deleteBackupButton.setBounds(70, 8, 20, 20);
		backupPanel.add(deleteBackupButton);

		extractBackupButton = new ImageButton("extract_button");
		extractBackupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SteamBackup b = manager.getSteamBackupByName(backupsTable.getValueAt(backupsTable.getSelectedRow(), 0).toString());
				if (b != null) {
					try {
						backupStatusLabel.setText("Extracting...");
						b.extract(manager.getData().getSteamdir());
						backupStatusLabel.setText("Extracted");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		extractBackupButton.setForeground(Color.WHITE);
		extractBackupButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		extractBackupButton.setBounds(570, 6, 24, 24);
		backupPanel.add(extractBackupButton);

		settingsPanel = new JPanel();
		settingsPanel.setLayout(null);
		settingsPanel.setBackground(Colors.DEFAULT_BACKGROUND);
		settingsPanel.setBounds(0, 36, 600, 364);
		frame.getContentPane().add(settingsPanel);

		steamDirTextField = new JTextField();
		steamDirTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == 10)
					saveSteamDir();
			}
		});
		steamDirTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				saveSteamDir();
			}
		});
		steamDirTextField.setBounds(128, 10, 462, 24);
		settingsPanel.add(steamDirTextField);
		steamDirTextField.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		steamDirTextField.setColumns(10);
		steamDirTextField.setBorder(new EmptyBorder(steamDirTextField.getInsets()));
		steamDirTextField.setBackground(Colors.DEFAULT_GRAY_BACKGROUND);
		steamDirTextField.setForeground(Colors.DEFAULT_TEXT);
		steamDirTextField.setSelectedTextColor(Colors.DEFAULT_TEXT);
		steamDirTextField.setSelectionColor(Colors.ACTIVE);
		steamDirTextField.setCaretColor(Colors.DEFAULT_TEXT);
		steamDirTextField.setText(manager.getData().getSteamdir().getAbsolutePath());

		JLabel steamDirLabel = new JLabel("Steam directory:");
		steamDirLabel.setForeground(Color.WHITE);
		steamDirLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		steamDirLabel.setBounds(10, 10, 108, 24);
		settingsPanel.add(steamDirLabel);

		JSeparator separator = new JSeparator();
		separator.setForeground(Colors.LIGHT_BACKGROUND);
		separator.setBackground(Colors.DARK_BACKGROUND);
		separator.setBounds(10, 45, 580, 1);
		settingsPanel.add(separator);

		JLabel label = new JLabel("Security password");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		label.setBounds(10, 57, 123, 24);
		settingsPanel.add(label);

		MinimalisticButton changeSecurityPasswordButton = new MinimalisticButton("Change security password", Colors.BUTTON_DEFAULT, Colors.BUTTON_ROLLOVER, Colors.ACTIVE, Colors.DARK_BACKGROUND);
		changeSecurityPasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChangeSecurityPasswordDialog(manager).setVisible(true);
			}
		});
		changeSecurityPasswordButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		changeSecurityPasswordButton.setBounds(10, 92, 212, 22);
		changeSecurityPasswordButton.setForeground(Colors.DEFAULT_TEXT);
		settingsPanel.add(changeSecurityPasswordButton);

		final LabelButton checkSecurityPasswordButton = new LabelButton("Accounts", new Font("Segoe UI Light", Font.PLAIN, 36), (Color) null, new Color(150, 150, 150), Color.WHITE, Color.WHITE);
		checkSecurityPasswordButton.setSelected(manager.getData().isCheckPassword());
		checkSecurityPasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.getData().setCheckPassword(checkSecurityPasswordButton.isSelected());
			}
		});
		checkSecurityPasswordButton.setText("Check security password on launch");
		checkSecurityPasswordButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		checkSecurityPasswordButton.setBounds(10, 121, 212, 20);
		settingsPanel.add(checkSecurityPasswordButton);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(90, 90, 90));
		separator_1.setBackground(new Color(25, 25, 25));
		separator_1.setBounds(10, 148, 580, 1);
		settingsPanel.add(separator_1);

		sentrysPanel = new JPanel();
		sentrysPanel.setLayout(null);
		sentrysPanel.setBackground(Colors.DEFAULT_BACKGROUND);
		sentrysPanel.setBounds(0, 36, 600, 364);
		frame.getContentPane().add(sentrysPanel);

		initSentrysTable();

		addSentryButton = new ImageButton("add_button");
		addSentryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (File f : openSentryFilesDialog()) {
					try {
						manager.addSentry(new PackedSentryFile(f));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				manager.refreshUI();
			}
		});
		addSentryButton.setBounds(10, 8, 20, 20);
		sentrysPanel.add(addSentryButton);
		addSentryButton.setForeground(Color.WHITE);
		addSentryButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));

		editSentryButton = new ImageButton("edit_button");
		editSentryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sentrysTable.getSelectedRowCount() > 0) {
					String selectedName = sentrysTable.getValueAt(sentrysTable.getSelectedRow(), 1).toString();
					PackedSentryFile sentry = manager.getSentryFileByFilename(selectedName);
					if (sentry != null) {
						new EditSentryFrame(sentry, manager).setVisible(true);;
					}
				}
			}
		});
		editSentryButton.setForeground(Color.WHITE);
		editSentryButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		editSentryButton.setBounds(40, 8, 20, 20);
		sentrysPanel.add(editSentryButton);

		deleteSentryButton = new ImageButton("delete_button");
		deleteSentryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sentrysTable.getSelectedRowCount() > 0) {
					List<PackedSentryFile> selected = new ArrayList<>();
					for (int r : sentrysTable.getSelectedRows()) {
						PackedSentryFile ca = manager.getSentryFileByFilename(sentrysTable.getValueAt(r, 1).toString());
						if (ca != null) {
							selected.add(ca);
						}
					}
					new DeleteSentrysDialog(selected, manager).setVisible(true);
				}
			}
		});
		deleteSentryButton.setForeground(Color.WHITE);
		deleteSentryButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		deleteSentryButton.setBounds(70, 8, 20, 20);
		sentrysPanel.add(deleteSentryButton);

		switchMenu(0);
	}

	private File openAccExportDialog(SteamAccount acc) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		chooser.setApproveButtonText("Save");
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setDialogTitle("Export account " + acc.getLogin());
		chooser.setSelectedFile(new File(acc.getLogin() + ".sa"));
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileHidingEnabled(false);
		chooser.showOpenDialog(frame);
		return chooser.getSelectedFile();
	}

	public void showPasswordCheck() {

	}

	protected void saveSteamDir() {
		File newSteamDir = new File(steamDirTextField.getText());
		if (newSteamDir.exists()) {
			manager.getData().setSteamdir(newSteamDir);
		} else {
			steamDirTextField.setText(manager.getData().getSteamdir().getAbsolutePath());
		}
	}

	public void setSelectedAccount(String account) {
		selectedAccountLabel.setText(account);
	}

	private void initAccsTable() {
		accountsTableContainer = new Container();
		accountsTableContainer.setLayout(new BorderLayout());
		accountsTableContainer.setBounds(0, 36, 600, 272);
		accountsTableContainer.setBackground(Color.GREEN);
		accountsTable = new MinimalisticTable(1, Colors.DEFAULT_BACKGROUND);
		accountsTable.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				manager.sortAccounts(accountsTable.getSortingColumn(), accountsTable.getSortingType() == 1 ? true : false);
			}
		});
		accountsTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 123 || e.getKeyCode() == 32) {
					launchSteamButton.doClick();
				}
				if (e.getKeyCode() == 127) {
					if (accountsTable.getSelectedRowCount() > 0) {
						List<SteamAccount> selected = new ArrayList<>();
						for (int r : accountsTable.getSelectedRows()) {
							SteamAccount ca = manager.getSteamAccountByName(accountsTable.getValueAt(r, 0).toString());
							if (ca != null) {
								selected.add(ca);
							}
						}
						new DeleteAccountDialog(selected, manager).setVisible(true);
					}
				}
			}
		});
		accountsTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
		"Account Name", "Password", "Last Login", "Description"
		}) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
		accountsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (waitingForDoubleClickOnAccsTable) {
					editAccountButton.doClick();
					waitingForDoubleClickOnAccsTable = false;
				} else {
					if (accountsTable.getSelectedRowCount() > 0) {
						String selectedName = accountsTable.getValueAt(accountsTable.getSelectedRow(), 0).toString();
						SteamAccount acc = manager.getSteamAccountByName(selectedName);
						if (acc != null) {
							manager.setSelectedAccount(acc);
						} else {
							manager.refreshUI();
						}
					}
					waitingForDoubleClickOnAccsTable = true;
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							waitingForDoubleClickOnAccsTable = false;
						}
					}, Main.DOUBLE_CLICK_DELAY);
				}
			}
		});
		accountsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		accountsTable.setForeground(Colors.DEFAULT_TEXT);
		accountsTable.setGridColor(Colors.DEFAULT_GRAY_BACKGROUND);
		accountsTable.setBackground(Colors.DEFAULT_BACKGROUND);
		accountsTable.setFont(tablesFont);
		accountsTable.getTableHeader().setDefaultRenderer(new MinimalisticTableHeaderRenderer(accountsTable, "arrows", Colors.DEFAULT_GRAY_BACKGROUND, tablesFont, Colors.DEFAULT_TEXT));
		accountsTable.getTableHeader().setEnabled(false);
		accountsTable.setDefaultRenderer(Object.class, new MinimalisticTableCellLabelRenderer(accountsTable, Colors.SHADOW_BACKGROUND, tablesFont, Color.WHITE, Colors.ACTIVE, Colors.DEFAULT_TEXT));
		accountsTable.setRowHeight(28);
		accountsTable.setBounds(0, 0, accountsTableContainer.getWidth(), accountsTableContainer.getHeight());
		accountsTableScrollPane = new JScrollPane(accountsTable) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintBorder(Graphics g) {
			}
		};
		accountsTableScrollPane.getVerticalScrollBar().setUI(new MinimalisticScrollbarUI(Colors.DEFAULT_BACKGROUND, Colors.ACTIVE, Colors.DARK_BACKGROUND));
		accountsTableScrollPane.setBackground(Colors.DARK_BACKGROUND);
		accountsTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		accountsTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		accountsTableScrollPane.setViewportBorder(null);
		accountsTableScrollPane.getViewport().setBackground(accountsTable.getBackground());
		accountsTableContainer.add(accountsTable.getTableHeader(), BorderLayout.PAGE_START);
		accountsTableContainer.add(accountsTableScrollPane);
		accountsPanel.add(accountsTableContainer);
		setAccountsTableColumnsSizes();
	}

	private void initSentrysTable() {
		sentrysTableContainer = new Container();
		sentrysTableContainer.setLayout(new BorderLayout());
		sentrysTableContainer.setBounds(0, 36, 600, 328);
		sentrysTableContainer.setBackground(Color.GREEN);
		sentrysTable = new MinimalisticTable(1, Colors.DEFAULT_BACKGROUND);
		sentrysTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
		"Name", "Filename"
		}) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
		sentrysTable.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				manager.sortSentrys(sentrysTable.getSortingColumn(), sentrysTable.getSortingType() == 1 ? true : false);
			}
		});
		sentrysTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (waitingForDoubleClickOnSentrysTable) {
					editSentryButton.doClick();
					waitingForDoubleClickOnSentrysTable = false;
				} else {
					waitingForDoubleClickOnSentrysTable = true;
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							waitingForDoubleClickOnSentrysTable = false;
						}
					}, Main.DOUBLE_CLICK_DELAY);
				}
			}
		});
		sentrysTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		sentrysTable.setForeground(Colors.DEFAULT_TEXT);
		sentrysTable.setGridColor(Colors.DEFAULT_GRAY_BACKGROUND);
		sentrysTable.setBackground(Colors.DEFAULT_BACKGROUND);
		sentrysTable.setFont(tablesFont);
		sentrysTable.getTableHeader().setDefaultRenderer(new MinimalisticTableHeaderRenderer(sentrysTable, "arrows", Colors.DEFAULT_GRAY_BACKGROUND, tablesFont, Colors.DEFAULT_TEXT));
		sentrysTable.getTableHeader().setEnabled(false);
		sentrysTable.setDefaultRenderer(Object.class, new MinimalisticTableCellLabelRenderer(sentrysTable, Colors.SHADOW_BACKGROUND, tablesFont, Color.WHITE, Colors.ACTIVE, Colors.DEFAULT_TEXT));
		sentrysTable.setRowHeight(28);
		sentrysTable.setBounds(0, 0, sentrysTableContainer.getWidth(), sentrysTableContainer.getHeight());
		sentrysTableScrollPane = new JScrollPane(sentrysTable) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintBorder(Graphics g) {
			}
		};
		sentrysTableScrollPane.getVerticalScrollBar().setUI(new MinimalisticScrollbarUI(Colors.DEFAULT_BACKGROUND, Colors.ACTIVE, Colors.DARK_BACKGROUND));
		sentrysTableScrollPane.setBackground(Colors.DARK_BACKGROUND);
		sentrysTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		sentrysTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sentrysTableScrollPane.setViewportBorder(null);
		sentrysTableScrollPane.getViewport().setBackground(sentrysTable.getBackground());
		sentrysTableContainer.add(sentrysTable.getTableHeader(), BorderLayout.PAGE_START);
		sentrysTableContainer.add(sentrysTableScrollPane);
		setSentrysTableColumnsSizes();
		sentrysPanel.add(sentrysTableContainer);
	}

	private void initBackupsTable() {
		backupsTableContainer = new Container();
		backupsTableContainer.setLayout(new BorderLayout());
		backupsTableContainer.setBounds(0, 36, 600, 328);
		backupsTableContainer.setBackground(Color.GREEN);
		backupsTable = new MinimalisticTable(1, Colors.DEFAULT_BACKGROUND);
		backupsTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
		"Name", "Created"
		}) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
		backupsTable.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				manager.sortBackups(backupsTable.getSortingColumn(), backupsTable.getSortingType() == 1 ? true : false);
			}
		});
		backupsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (waitingForDoubleClickOnBackupsTable) {
					editBackupButton.doClick();
					waitingForDoubleClickOnBackupsTable = false;
				} else {
					waitingForDoubleClickOnBackupsTable = true;
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							waitingForDoubleClickOnBackupsTable = false;
						}
					}, Main.DOUBLE_CLICK_DELAY);
				}
			}
		});
		backupsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		backupsTable.setForeground(Colors.DEFAULT_TEXT);
		backupsTable.setGridColor(Colors.DEFAULT_GRAY_BACKGROUND);
		backupsTable.setBackground(Colors.DEFAULT_BACKGROUND);
		backupsTable.setFont(tablesFont);
		backupsTable.getTableHeader().setDefaultRenderer(new MinimalisticTableHeaderRenderer(backupsTable, "arrows", Colors.DEFAULT_GRAY_BACKGROUND, tablesFont, Colors.DEFAULT_TEXT));
		backupsTable.getTableHeader().setEnabled(false);
		backupsTable.setDefaultRenderer(Object.class, new MinimalisticTableCellLabelRenderer(backupsTable, Colors.SHADOW_BACKGROUND, tablesFont, Color.WHITE, Colors.ACTIVE, Colors.DEFAULT_TEXT));
		backupsTable.setRowHeight(28);
		backupsTable.setBounds(0, 0, backupsTableContainer.getWidth(), backupsTableContainer.getHeight());
		backupsTableScrollPane = new JScrollPane(backupsTable) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintBorder(Graphics g) {
			}
		};
		backupsTableScrollPane.getVerticalScrollBar().setUI(new MinimalisticScrollbarUI(Colors.DEFAULT_BACKGROUND, Colors.ACTIVE, Colors.DARK_BACKGROUND));
		backupsTableScrollPane.setBackground(Colors.DARK_BACKGROUND);
		backupsTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		backupsTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		backupsTableScrollPane.setViewportBorder(null);
		backupsTableScrollPane.getViewport().setBackground(backupsTable.getBackground());
		backupsTableContainer.add(backupsTable.getTableHeader(), BorderLayout.PAGE_START);
		backupsTableContainer.add(backupsTableScrollPane);
		setBackupsTableColumnsSizes();

		backupStatusLabel = new JLabel("");
		backupStatusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		backupStatusLabel.setForeground(Color.WHITE);
		backupStatusLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		backupStatusLabel.setBounds(319, 0, 241, 36);
		backupPanel.add(backupStatusLabel);
		backupPanel.add(backupsTableContainer);
	}

	private void setBackupsTableColumnsSizes() {

	}

	private void setSentrysTableColumnsSizes() {

	}

	protected void setShowPasswords(boolean selected) {
		showPasswords = selected;
		manager.refreshUI();
	}

	private void setAccountsTableColumnsSizes() {

	}

	private File[] openSentryFilesDialog() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(manager.getData().getSteamdir());
		chooser.setDialogTitle("Open Sentry File");
		chooser.setMultiSelectionEnabled(true);
		chooser.setFileHidingEnabled(false);
		chooser.setFileFilter(new SentryFileFilter());
		chooser.showOpenDialog(frame);
		return chooser.getSelectedFiles();
	}

	public void refreshAccountsTable(List<SteamAccount> accounts) {
		String[][] tableData = new String[accounts.size()][accountsTable.getColumnCount()];
		for (int i = 0; i < accounts.size(); i++) {
			tableData[i] = accounts.get(i).asRow();
			if (!showPasswords) {
				tableData[i][1] = "********";
			}
		}
		accountsTable.setModel(new DefaultTableModel(tableData, new String[] {
		"Account Name", "Password", "Last Login", "Description"
		}) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
	}

	public void refreshBackupsTable(List<SteamBackup> backups) {
		String[][] tableData = new String[backups.size()][backupsTable.getColumnCount()];
		for (int i = 0; i < backups.size(); i++) {
			tableData[i][0] = backups.get(i).getName();
			tableData[i][1] = backups.get(i).getTimestampAsString();
		}
		backupsTable.setModel(new DefaultTableModel(tableData, new String[] {
		"Name", "Created"
		}) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
	}

	public void refreshSentrysTable(List<PackedSentryFile> sentrys) {
		String[][] tableData = new String[sentrys.size()][sentrysTable.getColumnCount()];
		for (int i = 0; i < sentrys.size(); i++) {
			tableData[i][0] = sentrys.get(i).getName();
			tableData[i][1] = sentrys.get(i).getFilename();
		}
		sentrysTable.setModel(new DefaultTableModel(tableData, new String[] {
		"Name", "Filename"
		}) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
	}

	private void switchMenu(int to) {
		accountsButton.setSelected(false);
		sentrysButton.setSelected(false);
		settingsButton.setSelected(false);
		helpMenuButton.setSelected(false);
		backupMenuButton.setSelected(false);
		sentrysPanel.setVisible(false);
		settingsPanel.setVisible(false);
		accountsPanel.setVisible(false);
		aboutPanel.setVisible(false);
		backupPanel.setVisible(false);
		switch (to) {
		case 0:
			accountsPanel.setVisible(true);
			accountsButton.setSelected(true);
			break;
		case 1:
			sentrysPanel.setVisible(true);
			sentrysButton.setSelected(true);
			break;
		case 2:
			backupPanel.setVisible(true);
			backupMenuButton.setSelected(true);
			break;
		case 3:
			settingsPanel.setVisible(true);
			settingsButton.setSelected(true);
			break;
		case 4:
			aboutPanel.setVisible(true);
			helpMenuButton.setSelected(true);
			break;
		default:
			break;
		}
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	public void dispose() {
		frame.dispose();
	}
}
