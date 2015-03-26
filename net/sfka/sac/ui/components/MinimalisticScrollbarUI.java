package net.sfka.sac.ui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class MinimalisticScrollbarUI extends BasicScrollBarUI {

	private Color defaultThumbColor, activeThumbColor, backgroundColor;

	public MinimalisticScrollbarUI(Color thumbColor, Color activeThumbColor, Color backgroundColor) {
		super();
		this.defaultThumbColor = thumbColor;
		this.activeThumbColor = activeThumbColor;
		this.backgroundColor = backgroundColor;
		scrollBarWidth = 16;
	}

	@Override
	protected void installComponents() {
		decrButton = createDecreaseButton(SOUTH);
		incrButton = createIncreaseButton(NORTH);
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
		JButton db = new JButton("");
		return db;
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		JButton db = new JButton("");
		return db;
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		Color thumbColor = isDragging ? activeThumbColor : defaultThumbColor;
		g.setColor(thumbColor);
		g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		g.setColor(backgroundColor);
		g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		paintTrack(g, c, getTrackBounds());
		paintThumb(g, c, getThumbBounds());
	}

	void updateThumbRect() {
		int max = scrollbar.getMaximum();
		int min = scrollbar.getMinimum();
		int value = scrollbar.getValue();
		int extent = scrollbar.getVisibleAmount();
		if (max - extent <= min) {
			if (scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {
				thumbRect.x = trackRect.x;
				thumbRect.y = trackRect.y;
				thumbRect.width = getMinimumThumbSize().width;
				thumbRect.height = trackRect.height;
			} else {
				thumbRect.x = trackRect.x;
				thumbRect.y = trackRect.y;
				thumbRect.width = trackRect.width;
				thumbRect.height = getMinimumThumbSize().height;
			}
		} else {
			if (scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {
				thumbRect.x = trackRect.x;
				thumbRect.width = Math.max(extent * trackRect.width / (max - min), getMinimumThumbSize().width);
				int availableWidth = trackRect.width - thumbRect.width;
				thumbRect.x += (value - min) * availableWidth / (max - min - extent);
				thumbRect.y = trackRect.y;
				thumbRect.height = trackRect.height;
			} else {
				thumbRect.x = trackRect.x;
				thumbRect.height = Math.max(extent * trackRect.height / (max - min), getMinimumThumbSize().height);
				int availableHeight = trackRect.height - thumbRect.height;
				thumbRect.y = trackRect.y + (value - min) * availableHeight / (max - min - extent);
				thumbRect.width = trackRect.width;
			}
		}
	}
	
	protected void layoutVScrollbar(JScrollBar sb) {
		Rectangle vr = new Rectangle();
		SwingUtilities.calculateInnerArea(scrollbar, vr);

		SwingUtilities.calculateInnerArea(scrollbar, trackRect);
		///trackRect.height -= incrDims.getHeight();
		//trackRect.height -= decrDims.getHeight();
		//trackRect.y += decrDims.getHeight();
		updateThumbRect();

		decrButton.setBounds(vr.x, vr.y, trackRect.width, trackRect.width);
		incrButton.setBounds(vr.x, trackRect.y + trackRect.height, trackRect.width, trackRect.width);
		scrollbar.repaint();
	}
}
