package group1.project.synthlab.ihm.port.out;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.port.ICPort;
import group1.project.synthlab.ihm.port.PPort;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class POutPort extends PPort implements IPOutPort {

	private static final long serialVersionUID = -5273540120761976911L;

	public POutPort(ICPort controller) {
		super(controller);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(100, 100, 220));
		Graphics2D ig = (Graphics2D) g;
		ig.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillOval(MARGIN, 0, SIZE, SIZE);
		super.paint(g);
	}


	public void refresh() {
		repaint();

	}
}
