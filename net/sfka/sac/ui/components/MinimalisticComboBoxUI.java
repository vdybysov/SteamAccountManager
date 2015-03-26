package net.sfka.sac.ui.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class MinimalisticComboBoxUI extends BasicComboBoxUI {
	
	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
	}

}
