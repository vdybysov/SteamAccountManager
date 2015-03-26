package net.sfka.sac.ui.components;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ButtonModel;
import javax.swing.JButton;

public class ImageButton extends JButton {
	private static final long serialVersionUID = 1L;

	public BufferedImage defaultTX;
	public BufferedImage rolloverTX;
	public BufferedImage pressedTX;
	public BufferedImage texture;

	public ImageButton(String textureName) {
		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setOpaque(false);
		setFocusable(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.texture = ImageUtils.getLocalImage(textureName);
		int i = texture.getWidth() / 3;

		defaultTX = texture.getSubimage(0, 0, i, texture.getHeight());
		rolloverTX = texture.getSubimage(i, 0, i, texture.getHeight());
		pressedTX = texture.getSubimage(i * 2, 0, i, texture.getHeight());
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
			if (buttonModel.isRollover()) {
				if (buttonModel.isPressed()) {
					g.drawImage(pressedTX, 0, 0, w, h, null);
				} else
					g.drawImage(rolloverTX, 0, 0, w, h, null);
			} else {
				g.drawImage(defaultTX, 0, 0, w, h, null);
			}
		}
		g.dispose();
		super.paintComponent(maing);
	}
}