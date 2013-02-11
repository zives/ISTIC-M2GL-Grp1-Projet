package group1.project.synthlab.ihm.module.osc;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class POSCModule extends PModule implements IPOSCModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected ICOSCModule controller;

	public POSCModule(final ICOSCModule controller) {
		super(controller);
		this.controller = controller;
		// Taille et couleur définie dans la super classe
		
		this.setSize(getWidth(), getHeight() - 30);
		this.setPreferredSize(this.getSize());

		
		// Ports
		PPort pportIn = (PPort) ((ICInPort) controller.getInPort())
				.getPresentation();
		PPort pportOut = (PPort) ((ICOutPort) controller.getOutPort())
				.getPresentation();
		pportIn.setLocation(10, getHeight() - pportIn.getHeight() - 5);
		pportOut.setLocation(getWidth() - pportIn.getWidth() - 10,
				pportIn.getY());

		// Ajouts des composants
		add(pportIn);
		add(pportOut);

		
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Creation du background
		g.setColor(new Color(50, 50, 50));
		
		// Coordonnées et largeur
		double w = getWidth() - 22;
		double x = (getWidth() - w) / 2;
		double y = 50;
		double h = 150;

		// Background
		RoundRectangle2D background = new RoundRectangle2D.Double(x, y , w, h, 10, 10);
		g2d.fill(background);

		// Font
		Font font = new Font("Arial", 0, 7);
		g.setFont(font);
		g.setColor(new Color(200,200,200));
		// Création graphique des lignes
		List<Double> values = controller.getValuesToDraw();
		if (values == null)
			return;
		int step = (int) (values.size() / w);
		if (step == 0)
			step = 1;
		Point last = null;
		for (int f = 0; f < values.size() ; f += step) {
			int tmpF = f;
			if (tmpF >= values.size())
				tmpF = values.size() - 1;
			Point p = new Point((int) (f / (double) values.size() * w + x), (int)((y  + h / 2) - (values.get(tmpF) * (h / 2))));
						
			if (last == null) {
				last = p;
				continue;
			}
			Line2D line = new Line2D.Double(last, p);
			last = p;
			g2d.draw(line);
		}
		
		

	}
}
