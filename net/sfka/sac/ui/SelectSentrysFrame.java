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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import net.sfka.sac.main.SteamAccountManager;
import net.sfka.sac.sentryfiles.PackedSentryFile;
import net.sfka.sac.sentryfiles.SentryFileFilter;
import net.sfka.sac.ui.components.Dragger;
import net.sfka.sac.ui.components.ImageButton;
import net.sfka.sac.ui.components.MinimalisticButton;
import net.sfka.sac.ui.components.MinimalisticScrollbarUI;
import net.sfka.sac.ui.components.MinimalisticTable;
import net.sfka.sac.ui.components.MinimalisticTableCellLabelRenderer;
import net.sfka.sac.ui.components.MinimalisticTableHeaderRenderer;

public class SelectSentrysFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private SteamAccountManager manager;

	private Container sentrysTableContainer;
	private MinimalisticTable sentrysTable;
	private JScrollPane sentrysTableScrollPane;

	/**
	 * Create the dialog.
	 */
	public SelectSentrysFrame(final CreateAccountFrame parent, final SteamAccountManager manager) {
		this.manager = manager;
		setUndecorated(true);
		setSize(389, 366);
		Dimension screen = getToolkit().getScreenSize();
		setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Colors.SHADOW_BACKGROUND);

		JPanel dragPanel = new JPanel();
		dragPanel.setBounds(0, 0, 389, 24);
		getContentPane().add(dragPanel);
		dragPanel.setLayout(null);
		dragPanel.setBackground(Colors.DEFAULT_GRAY_BACKGROUND);

		ImageButton closeButton = new ImageButton("close_button");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		closeButton.setBounds(364, 0, 24, 24);
		dragPanel.add(closeButton);

		Dragger dragger = new Dragger(this, "Select Sentry Files", Colors.DEFAULT_TEXT, Colors.DEFAULT_GRAY_BACKGROUND, Colors.DEFAULT_GRAY_BACKGROUND, new Font("Segoe UI Light", Font.PLAIN, 16));
		dragger.setBounds(0, 0, 388, 24);
		dragPanel.add(dragger);

		Font tablesFont = new Font("Segoe UI Light", Font.PLAIN, 14);

		sentrysTableContainer = new Container();
		sentrysTableContainer.setLayout(new BorderLayout());
		sentrysTableContainer.setBounds(10, 35, 270, 278);
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
		sentrysTableScrollPane.getVerticalScrollBar().setUI(new MinimalisticScrollbarUI(Colors.DEFAULT_BACKGROUND, Colors.ACTIVE, Colors.DARK_BACKGROUND));
		sentrysTableScrollPane.setBackground(Colors.DARK_BACKGROUND);
		sentrysTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		sentrysTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sentrysTableScrollPane.setViewportBorder(null);
		sentrysTableScrollPane.getViewport().setBackground(sentrysTable.getBackground());
		sentrysTableContainer.add(sentrysTable.getTableHeader(), BorderLayout.PAGE_START);
		sentrysTableContainer.add(sentrysTableScrollPane);
		getContentPane().add(sentrysTableContainer);
		setSentrysTableColumnsWidth();

		MinimalisticButton addSentryButton = new MinimalisticButton("Add account", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		addSentryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File[] sentrys = openSentryFilesDialog();
				for (File f : sentrys) {
					try {
						manager.addSentry(new PackedSentryFile(f));
						manager.refreshUI();
						setSentrysList(manager.getSentryFiles());
					} catch (IOException e) {
						System.out.println("Couldn't add " + f.getAbsolutePath());
						e.printStackTrace();
					}
				}
			}
		});
		addSentryButton.setText("Add");
		addSentryButton.setForeground(Color.WHITE);
		addSentryButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		addSentryButton.setBounds(286, 35, 93, 24);
		getContentPane().add(addSentryButton);

		MinimalisticButton removeSentryButton = new MinimalisticButton("Remove", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		removeSentryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int r = 0; r < sentrysTable.getSelectedRowCount(); r++) {
					PackedSentryFile psf = manager.getSentryFileByFilename(sentrysTable.getValueAt(r, 1).toString());
					if (psf != null) {
						manager.deleteSentryFile(psf);
					}
				}
				manager.refreshUI();
				setSentrysList(manager.getSentryFiles());
			}
		});
		removeSentryButton.setForeground(Color.WHITE);
		removeSentryButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		removeSentryButton.setBounds(286, 70, 93, 24);
		getContentPane().add(removeSentryButton);

		MinimalisticButton saveButton = new MinimalisticButton("OK", new Color(65, 65, 65), new Color(90, 90, 90), new Color(225, 140, 50), new Color(50, 50, 50));
		saveButton.setText("Select");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<PackedSentryFile> selected = new ArrayList<>();
				for (int r : sentrysTable.getSelectedRows()) {
					PackedSentryFile psf = manager.getSentryFileByFilename(sentrysTable.getValueAt(r, 1).toString());
					if (psf != null) {
						selected.add(psf);
					}
				}
				parent.addToSentrysList(selected);
				setVisible(false);
			}
		});
		saveButton.setForeground(Color.WHITE);
		saveButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		saveButton.setBounds(10, 322, 78, 30);
		getContentPane().add(saveButton);
		setSentrysList(manager.getSentryFiles());
	}

	private void setSentrysTableColumnsWidth() {
		sentrysTable.getColumnModel().getColumn(0).setMinWidth(110);
		sentrysTable.getColumnModel().getColumn(0).setMaxWidth(110);
		sentrysTable.getColumnModel().getColumn(1).setMaxWidth(160);
		sentrysTable.getColumnModel().getColumn(1).setMinWidth(160);
	}

	private void setSentrysList(List<PackedSentryFile> sentrys) {
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
		setSentrysTableColumnsWidth();
	}

	private File[] openSentryFilesDialog() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(manager.getData().getSteamdir());
		chooser.setDialogTitle("Open Sentry File");
		chooser.setMultiSelectionEnabled(true);
		chooser.setFileHidingEnabled(false);
		chooser.setFileFilter(new SentryFileFilter());
		chooser.showOpenDialog(this);
		return chooser.getSelectedFiles();
	}
}
