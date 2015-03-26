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
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import net.sfka.sac.accounts.SteamAccount;
import net.sfka.sac.main.SteamAccountManager;
import net.sfka.sac.ui.components.Dragger;
import net.sfka.sac.ui.components.ImageButton;
import net.sfka.sac.ui.components.MinimalisticButton;
import net.sfka.sac.ui.components.MinimalisticScrollbarUI;
import net.sfka.sac.ui.components.MinimalisticTable;
import net.sfka.sac.ui.components.MinimalisticTableCellLabelRenderer;
import net.sfka.sac.ui.components.MinimalisticTableHeaderRenderer;

public class ImportFromSteamFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private SteamAccountManager manager;
	private MinimalisticTable accountsTable;
	private JScrollPane accountsTableScrollPane;
	private Container accountsTableContainer;
	private Font tablesFont = new Font("Segoe UI Light", Font.PLAIN, 16);

	private List<SteamAccount> accs;

	/**
	 * Create the dialog.
	 */
	public ImportFromSteamFrame(final SteamAccountManager manager) {
		this.manager = manager;
		setUndecorated(true);
		setSize(430, 366);
		Dimension screen = getToolkit().getScreenSize();
		setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Colors.SHADOW_BACKGROUND);

		JPanel dragPanel = new JPanel();
		dragPanel.setBounds(0, 0, 430, 24);
		getContentPane().add(dragPanel);
		dragPanel.setLayout(null);
		dragPanel.setBackground(Colors.DEFAULT_GRAY_BACKGROUND);

		ImageButton closeButton = new ImageButton("close_button");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		closeButton.setBounds(405, 0, 25, 24);
		dragPanel.add(closeButton);

		Dragger dragger = new Dragger(this, "Select Sentry Files", Colors.DEFAULT_TEXT, Colors.DEFAULT_GRAY_BACKGROUND, Colors.DEFAULT_GRAY_BACKGROUND, new Font("Segoe UI Light", Font.PLAIN, 16));
		dragger.getTitle().setText("Import your Steam accounts");
		dragger.setBounds(0, 0, 430, 24);
		dragPanel.add(dragger);

		setAccountsTableColumnsSizes();

		MinimalisticButton importButton = new MinimalisticButton("OK", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		importButton.setText("Import");
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int r : accountsTable.getSelectedRows()) {
					SteamAccount acc = getAccByName(accountsTable.getValueAt(r, 0).toString());
					if (acc != null) {
						manager.addAccount(acc);
					}
				}
				setVisible(false);
			}
		});
		importButton.setForeground(Color.WHITE);
		importButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		importButton.setBounds(10, 325, 96, 30);
		getContentPane().add(importButton);
		initAccsTable();
		parseAccs();
	}

	private SteamAccount getAccByName(String name) {
		for (SteamAccount acc : accs) {
			if (acc.getLogin().equalsIgnoreCase(name))
				return acc;
		}
		return null;
	}

	private void parseAccs() {
		try {
			accs = SteamAccountManager.parseSteamLogins(manager.getData().getSteamdir());
			refreshAccountsTable(accs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initAccsTable() {
		accountsTableContainer = new Container();
		accountsTableContainer.setLayout(new BorderLayout());
		accountsTableContainer.setBounds(0, 30, 430, 280);
		accountsTableContainer.setBackground(Color.GREEN);
		accountsTable = new MinimalisticTable(1, Colors.DEFAULT_BACKGROUND);
		accountsTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
		"Account Name", "Last Login"
		}) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
		accountsTable.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int sortBy = accountsTable.getSortingColumn() == 1 ? 3 : accountsTable.getSortingColumn();
				boolean descend = accountsTable.getSortingType() == 1 ? true : false;
				refreshAccountsTable(manager.getAccountSorter().sort(accs, sortBy, descend));
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
		getContentPane().add(accountsTableContainer);
		setAccountsTableColumnsSizes();
	}

	private void setAccountsTableColumnsSizes() {
	}

	public void refreshAccountsTable(List<SteamAccount> accounts) {
		String[][] tableData = new String[accounts.size()][accountsTable.getColumnCount()];
		for (int i = 0; i < accounts.size(); i++) {
			tableData[i][0] = accounts.get(i).asRow()[0];
			tableData[i][1] = accounts.get(i).asRow()[2];
		}
		accountsTable.setModel(new DefaultTableModel(tableData, new String[] {
		"Account Name", "Last Login"
		}) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});
	}

}
