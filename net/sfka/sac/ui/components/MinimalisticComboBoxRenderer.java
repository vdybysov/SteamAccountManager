package net.sfka.sac.ui.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class MinimalisticComboBoxRenderer extends BasicComboBoxRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	@Override
	public void paintAll(Graphics g) {
		// TODO Auto-generated method stub
	//	super.paintAll(g);
	}
	
}
