package net.sfka.sac.ui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

public class MinimalisticTable extends JTable {

	private static final long serialVersionUID = 1L;

	private Color borderColor;
	private int borderWidth;
	private int sortingColumn, sortingType;

	public MinimalisticTable(int borderWidth, Color borderColor) {
		super();
		this.borderColor = borderColor;
		this.borderWidth = borderWidth;
		getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				int index = convertColumnIndexToModel(columnAtPoint(mouseEvent.getPoint()));
				if (index >= 0) {
					if(sortingColumn == index) {
						sortingType = (sortingType == 0) ? 1 : 0;
					}
					sortingColumn = index;
					getTableHeader().repaint();
				}
			};
		});
	}

	@Override
	protected void paintBorder(Graphics g) {
		//super.paintBorder(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(borderColor);
		g2d.drawRect(0, getHeight() - borderWidth, getWidth(), borderWidth);
		g2d.drawRect(0, 0, borderWidth - 1, getHeight());
		g2d.drawRect(getWidth() - borderWidth, 0, borderWidth, getHeight());
	}

	public int getSortingColumn() {
		return sortingColumn;
	}

	public void setSortingColumn(int sortingTable) {
		this.sortingColumn = sortingTable;
	}

	public int getSortingType() {
		return sortingType;
	}

	public void setSortingType(int sortingType) {
		this.sortingType = sortingType;
	}

}
