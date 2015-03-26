package net.sfka.sac.ui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MinimalisticTableCellLabelRenderer extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	//private JTable parentTable;

	private Color defaultBackground, defaultForeground;
	private Color selectedBackground, selectedForeground;

	public MinimalisticTableCellLabelRenderer(JTable parentTable, Color background, Font f, Color foreground, Color selectedBackground, Color selectedForeground) {
		//this.parentTable = parentTable;
		setFont(f);
		setOpaque(true);
		defaultBackground = background;
		defaultForeground = foreground;
		this.selectedBackground = selectedBackground;
		this.selectedForeground = selectedForeground;
		setForeground(foreground);
		setBackground(background);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D mg = (Graphics2D) g.create();
		mg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		mg.setColor(getBackground());
		mg.fillRect(0, 0, getWidth(), getHeight());
		mg.setColor(getForeground());
		mg.setFont(getFont());
		mg.drawString(getText(), 4, getHeight() / 2 + getFont().getSize() / 2);
		mg.setColor(new Color(20, 20, 20));
	/*	if (parentTable.getColumnCount() > 0 && getText().equals(parentTable.getColumnName(0)))
			mg.drawLine(0, 0, 0, getHeight() - 1);
		mg.drawLine(0, 0, getWidth(), 0);
		mg.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
		mg.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight() - 1);
		*/
		//mg.setColor(Color.RED);
		//mg.fillRect(0, 0, getWidth(), getHeight());
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value instanceof JLabel) {
			JLabel cs = (JLabel) value;
			setText(cs.getText());
			Color foreground =  isSelected ? selectedForeground : (cs.getForeground().getClass().equals(Color.class) ? cs.getForeground() : defaultForeground);
			setForeground(foreground);
			Color background =  isSelected ? selectedBackground : (cs.getBackground().getClass().equals(Color.class) ? cs.getBackground() : defaultBackground);
			setBackground(background);
			return this;
		} else if(value instanceof String) {
			setText(String.valueOf(value));
			Color foreground =  isSelected ? selectedForeground : defaultForeground;
			setForeground(foreground);
			Color background =  isSelected ? selectedBackground : defaultBackground;
			setBackground(background);
			return this;
		}
		return null;
	}

}
