package group1.project.synthlab.ihm.module.osc;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;
import group1.project.synthlab.ihm.tools.PTools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class POSCModule extends PModule implements IPOSCModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected transient ICOSCModule controller;
	protected transient final JSlider intervalSlider;
	protected transient final JLabel intervalLabel;

	public POSCModule(final ICOSCModule controller) {
		super(controller);
		this.controller = controller;

		this.setSize((int) (getWidth() * 1.3),
				(int) (getHeight() * 1.3 - (30 * 1.3)));
		this.setPreferredSize(this.getSize());
		onOffButton.setLocation(getWidth() - onOffButton.getWidth() - 13, 13);
		removeButton.setLocation(getWidth() - removeButton.getWidth()
				- onOffButton.getWidth() - 15, 13);

		// Ports
		PPort pportIn = (PPort) ((ICInPort) controller.getInPort())
				.getPresentation();
		PPort pportOut = (PPort) ((ICOutPort) controller.getOutPort())
				.getPresentation();
		pportIn.setLocation(10, getHeight() - pportIn.getHeight() - 5);
		pportOut.setLocation(getWidth() - pportIn.getWidth() - 10,
				pportIn.getY());

		intervalSlider = new JSlider();
		intervalSlider.setMaximum(200);
		intervalSlider.setMinimum(0);
		intervalSlider.setOrientation(JSlider.HORIZONTAL);
		intervalSlider.setSize(200, 35);
		intervalSlider.setFont(new Font("Arial", 0, 8));
		intervalSlider.setForeground(Color.LIGHT_GRAY);
		intervalSlider.setOpaque(false);
		intervalSlider.setFocusable(false);
		intervalSlider.setMajorTickSpacing(20);
		intervalSlider.setMinorTickSpacing(5);
		intervalSlider.setPaintTicks(true);
		intervalSlider.setPaintLabels(true);
		intervalSlider.setLocation(
				this.getWidth() / 2 - intervalSlider.getWidth() / 2 - 30, 300);
		intervalSlider.setSnapToTicks(true);
		intervalSlider.setVisible(true);

		intervalLabel = new JLabel(String.valueOf(intervalSlider.getValue())
				+ " ms", JLabel.CENTER);
		intervalLabel.setForeground(Color.LIGHT_GRAY);
		intervalLabel.setOpaque(false);
		intervalLabel.setSize(60, 30);
		intervalLabel.setBorder(null);
		intervalLabel.setPreferredSize(intervalLabel.getSize());
		intervalLabel.setLocation(
				intervalSlider.getX() + intervalSlider.getWidth(),
				intervalSlider.getY() - 7);

		// Ajouts des composants
		add(pportIn);
		add(pportOut);
		add(intervalSlider);
		add(intervalLabel);

		intervalSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (intervalSlider.getValue() <= 5)
					intervalSlider.setValue(5);
				controller.setInterval(intervalSlider.getValue());
				intervalLabel.setText(intervalSlider.getValue() + " ms");
			}
		});
		

	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.osc.IPOSCModule#refresh()
	 */
	public void refresh() {
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Creation du background
		g.setColor(new Color(50, 50, 50));

		// Coordonnées et largeur
		double w = getWidth() - 22;
		double x = (getWidth() - w) / 2;
		double y = 40;
		double h = getHeight() / 1.45;

		// Background
		Rectangle2D background = new Rectangle2D.Double(x, y, w, h);
		g2d.fill(background);
		g2d.setStroke(new BasicStroke(0.5f));

		// Font
		Font font = new Font("Arial", 0, 7);
		g.setFont(font);
		g.setColor(new Color(220, 220, 220));
		// Création graphique des lignes
		List<Double> values = controller.getValuesToDraw();
		if (values == null)
			return;
		int step = (int) (values.size() / w);
		if (step == 0)
			step = 1;
		Point last = null;
		Iterator<Double> iterator = values.iterator();
		int size = values.size();
		int f = 0;
		Line2D lineUpBound = new Line2D.Double(x, y, x + w - 1, y);
		Line2D lineBottomBound = new Line2D.Double(x, y + h, x + w -1, y + h);
		while (iterator.hasNext()) {
			Double value = iterator.next();
			if (value == null)
				break;
			Point p = new Point((int) (f / (double) size * w + x),
					(int) ((y + h / 2) - (value * (h / 2))));

			if (last == null) {
				last = p;
				continue;
			}

			Line2D line = new Line2D.Double(last, p);
			if (!(p.getY() < y && last.getY() < y)
					&& !(p.getY() > y + h && last.getY() > y + h)) {
				
				if (p.getY() < y) {
					Point intersec = PTools.intersection(lineUpBound, line);
					if (intersec != null)
						line.setLine(line.getP1(), intersec);
				} else if (p.getY() > y + h) {
					Point intersec = PTools.intersection(lineBottomBound, line);
					if (intersec != null)
						line.setLine(line.getP1(), intersec);
				}
				if (last.getY() < y) {
					Point intersec = PTools.intersection(lineUpBound, line);
					if (intersec != null)
						line.setLine(intersec, line.getP2());
				} else if (last.getY() > y + h) {
					Point intersec = PTools.intersection(lineBottomBound, line);
					if (intersec != null)
						line.setLine(intersec, line.getP2());
				}
				
				g2d.draw(line);
			}
						

			last = p;
			f++;
		}
		g2d.setStroke(new BasicStroke(1f));
		g.setColor(new Color(50, 50, 50));
		g2d.draw(lineUpBound);
		g2d.draw(lineBottomBound);
	}

	@Override
	public void updatePresentation() {
		super.updatePresentation();
		intervalSlider.setValue((int) (controller.getInterval()));
		intervalLabel.setText(intervalSlider.getValue() + " ms");

	}

}
