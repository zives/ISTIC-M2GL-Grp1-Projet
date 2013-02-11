package group1.project.synthlab.ihm.module.eqView;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;
import group1.project.synthlab.unitExtensions.filterSupervisor.IFilterAmplitudeObservable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PEQViewModule extends PModule implements IPEQViewModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected ICEQViewModule controller;
	protected List<List<Rectangle2D>> lines;
	protected RoundRectangle2D background;

	public PEQViewModule(final ICEQViewModule controller) {
		super(controller);
		this.controller = controller;
		// Taille et couleur définie dans la super classe

		this.lines = new ArrayList<List<Rectangle2D>>();

		// Ports
		PPort pportIn = (PPort) ((ICInPort) controller.getInPort()).getPresentation();
		pportIn.setLocation(10, 215);
		PPort pportOut = (PPort) ((ICOutPort) controller.getOutPort()).getPresentation();
		pportOut.setLocation(getWidth() - pportOut.getWidth() - 10, 215);


		// Ajouts des composants
		add(pportIn);
		add(pportOut);

		initLines();

		repaint();
		
		this.setSize(getWidth(), getHeight() - 30);
		this.setPreferredSize(this.getSize());

	}

	private void initLines() {
		double[] columnsF = controller.getFrequencies();

		// Nombre de lignes
		int nbLinesPerColumns = 35;

		// Hauteur des lignes
		int hMargin = 2;
		double hLines = 2;

		// Coordonnées et largeur
		double w = getWidth() - 22;
		double x = (getWidth() - w) / 2;
		double y = 50;

		// Largeurs
		int wMargin = 1;
		double wLines = w / columnsF.length - 2 * wMargin;
		
		// Backgroung
		background = new RoundRectangle2D.Double(x, y - 2,w,
				(hLines + hMargin ) * nbLinesPerColumns + 20, 10, 10);

		// Calculs
		for (int n = 0; n < columnsF.length; ++n) {
			this.lines.add(new ArrayList<Rectangle2D>());
			for (int i = nbLinesPerColumns; i >= 0; --i) {
				Rectangle2D line = new Rectangle2D.Double(x + n
						* (wLines + wMargin * 2) + wMargin, y + i * (hLines + hMargin),
						wLines, hLines);
				lines.get(n).add(line);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Creation du background
		g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		g.setColor(new Color(50, 50, 50));
		g2d.fill(background);

		// Font
		Font font = new Font("Arial", 0, 7);
		g.setFont(font);

		// Création graphique des lignes
		g2d.setStroke(new BasicStroke(1f));
		for (int f = 0; f < lines.size(); ++f) {
			// Get line max
			double max = controller.getMax(f);
			
			int maxG = (int) (max * lines.get(f).size());
			if (maxG >= lines.get(f).size())
				maxG = lines.get(f).size() - 1;

			// Draw freq text
			Rectangle2D firstRect = lines.get(f).get(0);
			g.setColor(Color.WHITE);
			FontMetrics metrics = g.getFontMetrics(font);
			String text = "";
			if (controller.getFrequencies()[f] > 999.0)
				text = String.valueOf(Math.round(controller.getFrequencies()[f] * 10 / 1000.0) / 10.0) + "k";
			else
				text = String.valueOf(Math.round(controller.getFrequencies()[f]));
			if (text.length() > 8)
				text = text.substring(0, 8);
			int wLabel = metrics.stringWidth(text);
			g.drawString(text, (int) (firstRect.getX() + firstRect.getWidth()
					/ 2 - wLabel / 2),
					(int) (firstRect.getY() + firstRect.getHeight() + 10));

			// Draw lines
			for (int i = 0; i < lines.get(f).size(); ++i) {

				if (i <= maxG && max >= 0.01) {
					if (i > lines.get(f).size()* 9 / 10)
						g.setColor(new Color(150, 70, 70));
					else if (i > lines.get(f).size()* 7 / 10)
						g.setColor(new Color(150, 125, 70));
					else if (i > lines.get(f).size()/ 2)
						g.setColor(new Color(150, 150, 70));
					else
						g.setColor(new Color(70, 150, 70));
				} else
					g.setColor(new Color(73,73,73));
				g2d.fill(lines.get(f).get(i));

			}
		}

	}
}
