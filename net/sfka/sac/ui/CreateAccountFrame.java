package net.sfka.sac.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import net.sfka.sac.accounts.SteamAccount;
import net.sfka.sac.main.SteamAccountManager;
import net.sfka.sac.sentryfiles.PackedSentryFile;
import net.sfka.sac.ui.components.Dragger;
import net.sfka.sac.ui.components.ImageButton;
import net.sfka.sac.ui.components.MinimalisticButton;
import net.sfka.sac.ui.components.MinimalisticScrollbarUI;
import net.sfka.sac.ui.components.MinimalisticTable;
import net.sfka.sac.ui.components.MinimalisticTableCellLabelRenderer;
import net.sfka.sac.ui.components.MinimalisticTableHeaderRenderer;

public class CreateAccountFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	protected SteamAccountManager manager;
	protected JTextField accNameTextField;
	protected JPasswordField passwordField;
	protected JTextField configFileTextField;

	protected Container sentrysTableContainer;
	protected MinimalisticTable sentrysTable;
	protected JScrollPane sentrysTableScrollPane;

	protected List<PackedSentryFile> sentrys;
	protected JLabel messageLabel;
	protected JTextField descriptionTextField;
	protected Dragger dragger;
	protected JTextField profileIDTextField;

	/**
	 * Create the dialog.
	 */
	public CreateAccountFrame(final SteamAccountManager manager) {
		sentrys = new ArrayList<>();
		this.manager = manager;
		setUndecorated(true);
		setSize(416, 438);
		Dimension screen = getToolkit().getScreenSize();
		setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Colors.SHADOW_BACKGROUND);

		JPanel dragPanel = new JPanel();
		dragPanel.setBounds(0, 0, 416, 24);
		getContentPane().add(dragPanel);
		dragPanel.setLayout(null);
		dragPanel.setBackground(Colors.DEFAULT_GRAY_BACKGROUND);

		ImageButton closeButton = new ImageButton("close_button");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		closeButton.setBounds(391, 0, 25, 24);
		dragPanel.add(closeButton);

		dragger = new Dragger(this, "New account", Colors.DEFAULT_TEXT, Colors.DEFAULT_GRAY_BACKGROUND, Colors.DEFAULT_GRAY_BACKGROUND, new Font("Segoe UI Light", Font.PLAIN, 16));
		dragger.setBounds(0, 0, 416, 24);
		dragPanel.add(dragger);

		JLabel accNameLabel = new JLabel("Account name:");
		accNameLabel.setForeground(Color.WHITE);
		accNameLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		accNameLabel.setBounds(10, 35, 102, 25);
		getContentPane().add(accNameLabel);

		accNameTextField = new JTextField();
		accNameTextField.setBounds(122, 35, 283, 25);
		getContentPane().add(accNameTextField);
		accNameTextField.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		accNameTextField.setColumns(10);
		accNameTextField.setBorder(new EmptyBorder(accNameTextField.getInsets()));
		accNameTextField.setBackground(Colors.DEFAULT_GRAY_BACKGROUND);
		accNameTextField.setForeground(Colors.DEFAULT_TEXT);
		accNameTextField.setSelectedTextColor(Colors.DEFAULT_TEXT);
		accNameTextField.setSelectionColor(Colors.ACTIVE);
		accNameTextField.setCaretColor(Colors.DEFAULT_TEXT);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		passwordLabel.setBounds(10, 71, 102, 25);
		getContentPane().add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setSelectionColor(new Color(225, 140, 50));
		passwordField.setSelectedTextColor(Color.WHITE);
		passwordField.setForeground(Color.WHITE);
		passwordField.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		passwordField.setColumns(10);
		passwordField.setCaretColor(Color.WHITE);
		passwordField.setBorder(new EmptyBorder(accNameTextField.getInsets()));
		passwordField.setBackground(new Color(50, 50, 50));
		passwordField.setBounds(122, 71, 283, 25);
		getContentPane().add(passwordField);

		JLabel lblConfigFile = new JLabel("Config file:");
		lblConfigFile.setForeground(Color.WHITE);
		lblConfigFile.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblConfigFile.setBounds(10, 143, 102, 25);
		getContentPane().add(lblConfigFile);

		configFileTextField = new JTextField();
		configFileTextField.setSelectionColor(new Color(225, 140, 50));
		configFileTextField.setSelectedTextColor(Color.WHITE);
		configFileTextField.setForeground(Color.WHITE);
		configFileTextField.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		configFileTextField.setColumns(10);
		configFileTextField.setCaretColor(Color.WHITE);
		configFileTextField.setBorder(new EmptyBorder(accNameTextField.getInsets()));
		configFileTextField.setBackground(new Color(50, 50, 50));
		configFileTextField.setBounds(122, 143, 180, 25);
		getContentPane().add(configFileTextField);

		MinimalisticButton mnmlstcbtnBrowse = new MinimalisticButton("Browse...", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		mnmlstcbtnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File cfgFile = openConfigFileChoosingDialog();
				if (cfgFile != null)
					configFileTextField.setText(cfgFile.getAbsolutePath());
			}
		});
		mnmlstcbtnBrowse.setForeground(Color.WHITE);
		mnmlstcbtnBrowse.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		mnmlstcbtnBrowse.setBounds(312, 144, 93, 24);
		getContentPane().add(mnmlstcbtnBrowse);

		JLabel lblSentryFiles = new JLabel("Sentry files:");
		lblSentryFiles.setForeground(Color.WHITE);
		lblSentryFiles.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblSentryFiles.setBounds(10, 179, 79, 25);
		getContentPane().add(lblSentryFiles);

		Font tablesFont = new Font("Segoe UI Light", Font.PLAIN, 16);

		sentrysTableContainer = new Container();
		sentrysTableContainer.setLayout(new BorderLayout());
		sentrysTableContainer.setBounds(10, 210, 292, 134);
		sentrysTableContainer.setBackground(Color.GREEN);
		sentrysTable = new MinimalisticTable(1, Colors.DEFAULT_BACKGROUND);
		sentrysTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
		"Name", "Filename",
		}) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
		sentrysTable.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				List<PackedSentryFile> toSort = new ArrayList<PackedSentryFile>();
				for (int r = 0; r < sentrysTable.getRowCount(); r++) {
					PackedSentryFile psf = manager.getSentryFileByFilename(sentrysTable.getValueAt(r, 1).toString());
					if (psf != null) {
						toSort.add(psf);
					}
				}
				setSentrysList(manager.getSentrysSorter().sort(toSort, sentrysTable.getSortingColumn(), sentrysTable.getSortingType() == 1 ? true : false));
			}
		});
		sentrysTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		sentrysTable.setForeground(Colors.DEFAULT_TEXT);
		sentrysTable.setGridColor(Colors.DEFAULT_GRAY_BACKGROUND);
		sentrysTable.setBackground(Colors.DEFAULT_BACKGROUND);
		sentrysTable.setFont(tablesFont);
		sentrysTable.getTableHeader().setDefaultRenderer(new MinimalisticTableHeaderRenderer(sentrysTable, "arrows", Colors.DEFAULT_GRAY_BACKGROUND, tablesFont, Colors.DEFAULT_TEXT));
		sentrysTable.getTableHeader().setEnabled(false);
		sentrysTable.setDefaultRenderer(Object.class, new MinimalisticTableCellLabelRenderer(sentrysTable, Colors.DEFAULT_BACKGROUND, tablesFont, Color.WHITE, Colors.ACTIVE, Colors.DEFAULT_TEXT));
		sentrysTable.setRowHeight(20);
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
		getContentPane().add(sentrysTableContainer);

		MinimalisticButton addSentryButton = new MinimalisticButton("Add account", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		addSentryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openSentrysSelectionFrame();
			}
		});
		addSentryButton.setText("Add");
		addSentryButton.setForeground(Color.WHITE);
		addSentryButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		addSentryButton.setBounds(312, 210, 93, 24);
		getContentPane().add(addSentryButton);

		MinimalisticButton removeSentryButton = new MinimalisticButton("Remove", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		removeSentryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<PackedSentryFile> newList = new ArrayList<>();
				for (int r = 0; r < sentrysTable.getRowCount(); r++) {
					boolean add = true;
					for (int s : sentrysTable.getSelectedRows()) {
						if (s == r)
							add = false;
					}
					if (add) {
						PackedSentryFile psf = manager.getSentryFileByFilename(sentrysTable.getValueAt(r, 1).toString());
						if (psf != null) {
							newList.add(psf);
						}
					}
				}
				setSentrysList(newList);
			}
		});
		removeSentryButton.setForeground(Color.WHITE);
		removeSentryButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		removeSentryButton.setBounds(312, 245, 93, 24);
		getContentPane().add(removeSentryButton);

		MinimalisticButton saveButton = new MinimalisticButton("Save", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAccount();
			}
		});
		saveButton.setForeground(Color.WHITE);
		saveButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		saveButton.setBounds(10, 394, 79, 30);
		getContentPane().add(saveButton);

		messageLabel = new JLabel("");
		messageLabel.setForeground(Color.RED);
		messageLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		messageLabel.setBounds(99, 394, 306, 30);
		getContentPane().add(messageLabel);

		JLabel descriptionLabel = new JLabel("Description:");
		descriptionLabel.setForeground(Color.WHITE);
		descriptionLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		descriptionLabel.setBounds(10, 358, 102, 25);
		getContentPane().add(descriptionLabel);

		descriptionTextField = new JTextField();
		descriptionTextField.setSelectionColor(new Color(225, 140, 50));
		descriptionTextField.setSelectedTextColor(Color.WHITE);
		descriptionTextField.setForeground(Color.WHITE);
		descriptionTextField.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		descriptionTextField.setColumns(10);
		descriptionTextField.setCaretColor(Color.WHITE);
		descriptionTextField.setBorder(new EmptyBorder(accNameTextField.getInsets()));
		descriptionTextField.setBackground(new Color(50, 50, 50));
		descriptionTextField.setBounds(122, 358, 283, 25);
		getContentPane().add(descriptionTextField);

		profileIDTextField = new JTextField();
		profileIDTextField.setSelectionColor(new Color(225, 140, 50));
		profileIDTextField.setSelectedTextColor(Color.WHITE);
		profileIDTextField.setForeground(Color.WHITE);
		profileIDTextField.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		profileIDTextField.setColumns(10);
		profileIDTextField.setCaretColor(Color.WHITE);
		profileIDTextField.setBorder(new EmptyBorder(accNameTextField.getInsets()));
		profileIDTextField.setBackground(new Color(50, 50, 50));
		profileIDTextField.setBounds(122, 107, 283, 25);
		getContentPane().add(profileIDTextField);

		JLabel profileIDLabel = new JLabel("Profile ID:");
		profileIDLabel.setForeground(Color.WHITE);
		profileIDLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		profileIDLabel.setBounds(10, 107, 102, 25);
		getContentPane().add(profileIDLabel);
	}

	@Override
	public void setTitle(String title) {
		super.setTitle(title);
		dragger.getTitle().setText(title);
	}

	protected void saveAccount() {
		messageLabel.setVisible(false);
		String accName = accNameTextField.getText();
		if (accName.length() == 0) {
			messageLabel.setText("Specify the account name!");
			messageLabel.setVisible(true);
			return;
		}
		for (SteamAccount acc : manager.getAccounts()) {
			if (acc.getLogin().equalsIgnoreCase(accName)) {
				messageLabel.setText("Account " + accName + " already exists!");
				messageLabel.setVisible(true);
				return;
			}
		}
		String pass = String.valueOf(passwordField.getPassword());
		if (pass.length() == 0) {
			messageLabel.setText("Specify the password!");
			messageLabel.setVisible(true);
			return;
		}
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
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		List<PackedSentryFile> accSentrys = new ArrayList<>();
		for (int r : sentrysTable.getSelectedRows()) {
			PackedSentryFile psf = manager.getSentryFileByFilename(sentrysTable.getValueAt(r, 1).toString());
			if (psf != null) {
				accSentrys.add(psf);
			}
		}
		SteamAccount newAccount = new SteamAccount(accName, pass, profileIDTextField.getText(), config, descriptionTextField.getText(), accSentrys, System.currentTimeMillis());
		manager.addAccount(newAccount);
		manager.saveAccounts();
		manager.refreshUI();
		dispose();
	}

	public void addToSentrysList(List<PackedSentryFile> sentrys) {
		List<PackedSentryFile> saved = new ArrayList<>();
		for (int r = 0; r < sentrysTable.getRowCount(); r++) {
			PackedSentryFile cs;
			if ((cs = manager.getSentryFileByFilename(sentrysTable.getValueAt(r, 0).toString())) != null) {
				saved.add(cs);
			}
		}
		for (PackedSentryFile psf : saved) {
			if (!sentrys.contains(psf)) {
				sentrys.add(psf);
			}
		}
		setSentrysList(sentrys);
	}

	public void setSentrysList(List<PackedSentryFile> sentrys) {
		String[][] tableData = new String[sentrys.size()][2];
		for (int i = 0; i < sentrys.size(); i++) {
			tableData[i][0] = sentrys.get(i).getName();
			tableData[i][1] = sentrys.get(i).getFilename();
		}
		sentrysTable.setModel(new DefaultTableModel(tableData, new String[] {
		"Name", "Filename",
		}) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
	}

	private File openConfigFileChoosingDialog() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(manager.getData().getSteamdir());
		chooser.setDialogTitle("Open config.vdf");
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileHidingEnabled(false);
		chooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "";
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory())
					return true;
				String ext = f.getName();
				String[] extSplit = ext.split("\\.");
				if (extSplit.length > 0) {
					ext = extSplit[extSplit.length - 1];
					if (ext.equalsIgnoreCase("vdf")) {
						return true;
					}
				}
				return false;
			}
		});
		chooser.showOpenDialog(this);
		return chooser.getSelectedFile();
	}

	protected void openSentrysSelectionFrame() {
		new SelectSentrysFrame(this, manager).setVisible(true);
	}
}
