package net.sfka.sac.ui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class MinimalisticTableHeaderRenderer extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	private JTable parentTable;

	private BufferedImage arrowUp, arrowDown;
	private int columnIndex;

	public MinimalisticTableHeaderRenderer(JTable parentTable, String texture, Color background, Font f, Color foreground) {
		this.parentTable = parentTable;
		if (texture != null) {
			BufferedImage textureImg = ImageUtils.getLocalImage(texture);
			arrowUp = textureImg.getSubimage(0, 0, textureImg.getWidth() / 2, textureImg.getHeight());
			arrowDown = textureImg.getSubimage(textureImg.getWidth() / 2, 0, textureImg.getWidth() / 2, textureImg.getHeight());
		}
		setFont(f);
		setOpaque(true);
		setForeground(foreground);
		setBackground(background);
		setBorder(new EmptyBorder(getInsets()));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D mg = (Graphics2D) g.create();
		mg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		mg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		mg.setColor(getBackground());
		mg.fillRect(0, 0, getWidth(), getHeight());
		mg.setColor(getForeground());
		mg.setFont(getFont());
		mg.drawString(getText().toUpperCase(), 4, getHeight() / 2 + getFont().getSize() / 2);

		if (parentTable instanceof MinimalisticTable) {
			if (columnIndex == ((MinimalisticTable) parentTable).getSortingColumn()) {
				int sorting = ((MinimalisticTable) parentTable).getSortingType();
				BufferedImage arrow = null;
				switch (sorting) {
				case 0:
					arrow = arrowUp;
					break;
				case 1:
					arrow = arrowDown;
					break;
				default:
					break;
				}
				if (arrow != null) {
					mg.drawImage(arrow, getWidth() - getHeight(), 4, getHeight()-4, getHeight()-6, null);
					Color sc = mg.getColor();
					Color nc = new Color(255, 255, 255, 50);
					mg.setColor(nc);
					mg.fillRect(0, 0, getWidth(), getHeight());
					mg.setColor(sc);
				}
			}
		}
		mg.setColor(new Color(20, 20, 20));
		if (parentTable.getColumnCount() > 0 && getText().equals(parentTable.getColumnName(0)))
			mg.drawLine(0, 0, 0, getHeight() - 1);
		mg.drawLine(0, 0, getWidth(), 0);
		mg.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
		mg.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight() - 1);
		//super.paintComponent(g);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		setColumnIndex(column);
		if (value instanceof JLabel) {
			JLabel l = (JLabel) value;
			setText(l.getText());
			//setBackground(l.getBackground());
			//setForeground(l.getForeground());
			return this;
		} else {
			setText(value.toString());
			return this;
		}
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

}
