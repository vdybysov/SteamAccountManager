package net.sfka.sac.ui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ButtonModel;
import javax.swing.JButton;

public class MinimalisticImageButton extends JButton {
	private static final long serialVersionUID = 1L;

	public BufferedImage defaultTX;
	public BufferedImage texture;

	public MinimalisticImageButton(String textureName) {
		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setOpaque(false);
		setFocusable(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.texture = ImageUtils.getLocalImage(textureName);
		defaultTX = texture;
	}

	protected void paintComponent(Graphics maing) {
		ButtonModel buttonModel = getModel();
		Graphics2D g = (Graphics2D) maing.create();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		int w = getWidth();
		int h = getHeight();
		if (isEnabled()) {
			g.drawImage(defaultTX, 0, 0, w, h, null);
			if (buttonModel.isRollover()) {
				if (buttonModel.isPressed()) {
					g.setColor(new Color(1, 1, 1, 0.5F));
				} else {
					g.setColor(new Color(1, 1, 1, 0.25F));
				}
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		}
		g.dispose();
		super.paintComponent(maing);
	}
}