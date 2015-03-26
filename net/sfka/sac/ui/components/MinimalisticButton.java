package net.sfka.sac.ui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.ButtonModel;
import javax.swing.JButton;


public class MinimalisticButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	private Color normal, rollover, pressed, locked;
	
	/**
	 * 
	 * @param text
	 * @param normal
	 * @param rollover
	 * @param pressed
	 * @param locked
	 */
	
	public MinimalisticButton(String text, Color normal, Color rollover, Color pressed, Color locked) {
		this.normal = normal;
		this.rollover = rollover;
		this.pressed = pressed;
		this.locked = locked;
		setText(text);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setOpaque(false);
		setFocusable(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public Point getMousePos() {
		return this.getMousePosition();
	}

	protected void paintComponent(Graphics maing) {
		ButtonModel buttonModel = getModel();
		Graphics2D g = (Graphics2D) maing.create();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		int w = getWidth();
		int h = getHeight();

		if (!isEnabled()) {
			g.setColor(locked);
		} else if (buttonModel.isRollover()) {
			if (buttonModel.isPressed()) {
				g.setColor(pressed);
			} else {
				g.setColor(rollover);
			}
		} else {
			g.setColor(normal);
		}
		
		g.fillRect(0, 0, w, h);
			
		g.dispose();
		
		super.paintComponent(maing);
		
	}

	public Color getNormal() {
		return normal;
	}

	public void setNormal(Color normal) {
		this.normal = normal;
	}

	public Color getRollover() {
		return rollover;
	}

	public void setRollover(Color rollover) {
		this.rollover = rollover;
	}

	public Color getPressed() {
		return pressed;
	}

	public void setPressed(Color pressed) {
		this.pressed = pressed;
	}

	public Color getLocked() {
		return locked;
	}

	public void setLocked(Color locked) {
		this.locked = locked;
	}

}
