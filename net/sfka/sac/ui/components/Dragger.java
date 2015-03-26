package net.sfka.sac.ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Dragger extends JPanel {
	private static final long serialVersionUID = 1L;

	private int x = 0;
	private int y = 0;

	private JLabel title;
	private Color lineColor, activeColor, inactiveColor;

	public Dragger(final JFrame parent, String titleText, Color titleColor, final Color activeColor, final Color inactiveColor, Font font) {
		this.lineColor = titleColor;
		this.inactiveColor = inactiveColor;
		this.activeColor = activeColor;
		setOpaque(false);
		setLayout(new BorderLayout());
		title = new JLabel(titleText);
		title.setHorizontalAlignment(SwingConstants.LEFT);
		title.setFont(font);
		title.setForeground(titleColor);
		add(title, BorderLayout.CENTER);
		setBorder(new EmptyBorder(0, 10, 0, 10));
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				parent.setLocation(e.getX() + parent.getX() - x, e.getY() + parent.getY() - y);
			}
		});
		addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}

			public void mouseClicked(MouseEvent event) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}
		});
		parent.addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent arg0) {
				setBackground(activeColor);
				repaint();
			}

			public void windowLostFocus(WindowEvent arg0) {
				setBackground(inactiveColor);
				repaint();
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		g2.setColor(getBackground());
		g2.fillRect(0, 0, getWidth(), getHeight());
	}

	public JLabel getTitle() {
		return title;
	}
	
	public void setTitle(JLabel title) {
		this.title = title;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public Color getInactLineColor() {
		return inactiveColor;
	}

	public void setInactLineColor(Color inactLineColor) {
		this.inactiveColor = inactLineColor;
	}

	public Color getActiveColor() {
		return activeColor;
	}

	public void setActiveColor(Color activeColor) {
		this.activeColor = activeColor;
	}

	public Color getInactiveColor() {
		return inactiveColor;
	}

	public void setInactiveColor(Color inactiveColor) {
		this.inactiveColor = inactiveColor;
	}
}