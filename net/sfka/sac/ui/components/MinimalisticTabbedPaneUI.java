package net.sfka.sac.ui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class MinimalisticTabbedPaneUI extends BasicTabbedPaneUI {

	private static final Insets NO_INSETS = new Insets(0, 0, 0, 0);
	private ColorSet selectedColorSet;
	private ColorSet defaultColorSet;
	private ColorSet hoverColorSet;
	private boolean contentTopBorderDrawn = true;
	private Color lineColor = new Color(158, 158, 158);
	private Insets contentInsets = new Insets(10, 10, 10, 10);
	private int lastRollOverTab = -1;

	public static ComponentUI createUI(JComponent c, Color defaultBackColor, Color defaultTitleColor, Color hoverBackColor, Color hoverTitleColor, Color selectedBackColor, Color selectedTitleColor) {
		return new MinimalisticTabbedPaneUI(defaultBackColor, defaultTitleColor, hoverBackColor, hoverTitleColor, selectedBackColor, selectedTitleColor);
	}

	public MinimalisticTabbedPaneUI(Color defaultBackColor, Color defaultTitleColor, Color hoverBackColor, Color hoverTitleColor, Color selectedBackColor, Color selectedTitleColor) {

		selectedColorSet = new ColorSet();
		selectedColorSet.backColor = selectedBackColor;
		selectedColorSet.titleColor = selectedTitleColor;

		defaultColorSet = new ColorSet();
		defaultColorSet.backColor = defaultBackColor;
		defaultColorSet.titleColor = defaultTitleColor;

		hoverColorSet = new ColorSet();
		hoverColorSet.backColor = hoverBackColor;
		hoverColorSet.titleColor = hoverTitleColor;

		maxTabHeight = 20;

		setContentInsets(0);
	}

	public void setContentTopBorderDrawn(boolean b) {
		contentTopBorderDrawn = b;
	}

	public void setContentInsets(Insets i) {
		contentInsets = i;
	}

	public void setContentInsets(int i) {
		contentInsets = new Insets(i, i, i, i);
	}

	public int getTabRunCount(JTabbedPane pane) {
		return 1;
	}

	protected void installDefaults() {
		super.installDefaults();

		RollOverListener l = new RollOverListener();
		tabPane.addMouseListener(l);
		tabPane.addMouseMotionListener(l);

		tabAreaInsets = NO_INSETS;
		tabInsets = new Insets(0, 0, 0, 1);
	}

	protected boolean scrollableTabLayoutEnabled() {
		return false;
	}

	protected Insets getContentBorderInsets(int tabPlacement) {
		return contentInsets;
	}

	protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
		return 21;
	}

	protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
		int w = super.calculateTabWidth(tabPlacement, tabIndex, metrics);
		int wid = metrics.charWidth('M');
		w += wid * 2;
		return w;
	}

	protected int calculateMaxTabHeight(int tabPlacement) {
		return 21;
	}

	protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(defaultColorSet.backColor);
		g2d.fillRect(0, 0, tabPane.getWidth(), 10);

		g2d.setPaint(defaultColorSet.backColor);
		g2d.fillRect(0, 10, tabPane.getWidth(), 11);
		super.paintTabArea(g, tabPlacement, selectedIndex);

		if (contentTopBorderDrawn) {
			g2d.setColor(lineColor);
			//g2d.drawLine(0, 20, tabPane.getWidth() - 1, 20);
		}
	}

	protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
		g.setFont(font);
		ColorSet cs;
		if (isSelected) {
			cs = selectedColorSet;
		} else if (getRolloverTab() == tabIndex) {
			cs = hoverColorSet;
		} else {
			cs = defaultColorSet;
		}
		g.setColor(cs.titleColor);
		g.drawString(title, textRect.x, textRect.y + textRect.height / 2 + 5);
	}

	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		Graphics2D g2d = (Graphics2D) g;
		ColorSet colorSet;

		Rectangle rect = rects[tabIndex];

		if (isSelected) {
			colorSet = selectedColorSet;
		} else if (getRolloverTab() == tabIndex) {
			colorSet = hoverColorSet;
		} else {
			colorSet = defaultColorSet;
		}

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int width = rect.width;
		int xpos = rect.x;
		if (tabIndex > 0) {
			width--;
			xpos++;
		}
		g2d.setPaint(colorSet.backColor);
		g2d.fillRect(xpos, 0, width, h);
		g2d.setPaint(colorSet.titleColor);

	}

	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		// Do nothing
	}

	protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {

	}

	protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
		// Do nothing
	}

	protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
		// Do nothing
	}

	protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
		// Do nothing
	}

	protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
		// Do nothing
	}

	protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected) {
		return 0;
	}

	private class ColorSet {
		Color backColor, titleColor;
	}

	private class RollOverListener implements MouseMotionListener, MouseListener {

		public void mouseDragged(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e) {
			checkRollOver();
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
			checkRollOver();
		}

		public void mouseExited(MouseEvent e) {
			tabPane.repaint();
		}

		private void checkRollOver() {
			int currentRollOver = getRolloverTab();
			if (currentRollOver != lastRollOverTab) {
				lastRollOverTab = currentRollOver;
				Rectangle tabsRect = new Rectangle(0, 0, tabPane.getWidth(), 20);
				tabPane.repaint(tabsRect);
			}
		}
	}
}