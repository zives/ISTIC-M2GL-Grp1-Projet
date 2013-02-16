package group1.project.synthlab.ihm.port.in;

import group1.project.synthlab.ihm.port.ICPort;
import group1.project.synthlab.ihm.port.PPort;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * @author Groupe 1
 * Presnetation de port d'entree
 */
public class PInPort extends PPort implements IPInPort {

	private static final long serialVersionUID = 30814597301574881L;

	public PInPort(ICPort controller) {
		super(controller);
	}

	@Override
	public void paintComponent(Graphics g) {	
		//On dessine un contour pour indiquer qu'il s'agit d'un port d'entree
		g.setColor(new Color(70, 160, 70));
		Graphics2D ig = (Graphics2D) g;
		ig.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillOval(MARGIN, 0, SIZE, SIZE);
		super.paintComponent(g);
	}



	public void refresh() {
		repaint();
		
	}
}
