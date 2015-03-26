package net.sfka.sac.ui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ButtonModel;
import javax.swing.JRadioButton;

public class LabelButton extends JRadioButton {

	private static final long serialVersionUID = 1L;

	private Color activeBackgroundColor, defaultColor, rolloverColor, activeColor;
	private Font font;
	private String text;

	public LabelButton(String text, Font font, Color activeBackgroundColor, Color defaultColor, Color rolloverColor, Color activeColor) {
		this.activeBackgroundColor = activeBackgroundColor;
		this.defaultColor = defaultColor;
		this.rolloverColor = rolloverColor;
		this.activeColor = activeColor;
		this.text = text;
		this.font = font;
		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setOpaque(false);
		setFocusable(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void paintComponents(Graphics g) {
	}

	protected void paintComponent(Graphics maing) {
		ButtonModel buttonModel = getModel();
		Graphics2D g = (Graphics2D) maing.create();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		int w = getWidth();
		int h = getHeight();

		g.setFont(font);

		if (buttonModel.isRollover()) {
			g.setColor(rolloverColor);
		} else {
			g.setColor(defaultColor);
		}

		if (buttonModel.isSelected()) {
			if(activeBackgroundColor != null) {
				g.setColor(activeBackgroundColor);
				g.fillRect(0, 0, w, h);
			}
			g.setColor(activeColor);
		}

		g.drawString(text, 2, h / 2 + font.getSize2D() / 2 - Math.round(font.getSize2D() * 0.2F));

		//g.setColor(Color.RED);
		//g.drawRect(0, 0, getWidth()-1, h-1);
		g.dispose();
	}

	public Color getActiveBackgroundColor() {
		return activeBackgroundColor;
	}

	public void setActiveBackgroundColor(Color activeBackgroundColor) {
		this.activeBackgroundColor = activeBackgroundColor;
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
	}

	public Color getRolloverColor() {
		return rolloverColor;
	}

	public void setRolloverColor(Color rolloverColor) {
		this.rolloverColor = rolloverColor;
	}

	public Color getActiveColor() {
		return activeColor;
	}

	public void setActiveColor(Color activeColor) {
		this.activeColor = activeColor;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
