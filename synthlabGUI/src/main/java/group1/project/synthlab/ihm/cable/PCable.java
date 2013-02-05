package group1.project.synthlab.ihm.cable;

import group1.project.synthlab.ihm.port.IPPort;
import group1.project.synthlab.ihm.port.PPort;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author Groupe 1
 * Presentation du cable
 */
public class PCable extends JPanel implements IPCable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1542461431128718349L;
	
	protected ICCable controller; //le controleur
	
	protected Point p1; //le point 1 (extremite du cable)
	protected Point p2; //le point 2 (extremite 2)
	
	QuadCurve2D graphicLink; //la forme geometrique du lien
	
	private PCable self; //la presentation elle-meme
	
	private float animation; //une valeur arbitraire pour animer la cable
	private Timer timerAnimation; // le timer qui anime le cable
		
	private Point pBezier; //le point d'attraction du cable pour la courbure

	public PCable(final CCable controller) {
		this.controller = controller;
		this.self = this;
		this.p1 = new Point(10, 10);
		this.p2 = new Point(20, 20);

		this.animation = 10;
		
		setLocation(0, 0);
		setSize(1, 1);
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
		
		timerAnimation = new Timer();
		timerAnimation.schedule(new TimerTask() {

			@Override
			public void run() {
				animation -= 2;
				if (animation <= 0)
					animation = 2000;
				repaint();
			}
		}, 100, 50);

	}

	@Override
	protected void finalize() throws Throwable {
		destruct();
		super.finalize();
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.cable.IPCable#destruct()
	 */
	public void destruct() {
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.cable.IPCable#setP1(int, int)
	 */
	public void setP1(int x, int y) {
		p1 = new Point(x, y);
		repaint();
		((JLayeredPane) this.getParent()).moveToFront(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.cable.IPCable#setP1(group1.project.synthlab.ihm.port.IPPort)
	 */
	public void setP1(IPPort port) {
		PPort p = (PPort) port;
		p1 = new Point(p.getX() + p.getParent().getX() + PPort.SIZE / 2
				+ PPort.getMARGIN(), p.getY() + p.getParent().getY()
				+ PPort.SIZE / 2);

		repaint();
		((JLayeredPane) this.getParent()).moveToFront(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.cable.IPCable#setP2(int, int)
	 */
	public void setP2(int x, int y) {
		p2 = new Point(x, y);
		repaint();
		((JLayeredPane) this.getParent()).moveToFront(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.cable.IPCable#setP2(group1.project.synthlab.ihm.port.IPPort)
	 */
	public void setP2(IPPort port) {
		PPort p = (PPort) port;
		p2 = new Point(p.getX() + p.getParent().getX() + PPort.SIZE / 2
				+ PPort.getMARGIN(), p.getY() + p.getParent().getY()
				+ PPort.SIZE / 2);
		repaint();

		((JLayeredPane) this.getParent()).moveToFront(this);

	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D ig = (Graphics2D) g;
		ig.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		//On trouve les bornes du rectangle transparant qui contiendra le cable
		int x = 0;
		int y = 0;
		int w = 0;
		int h = 0;
		if (p1.getX() < p2.getX()) {
			x = (int) (p1.getX() - 10);
			w = (int) (p2.getX() - p1.getX() + 20);
		} else {
			x = (int) (p2.getX() - 10);
			w = (int) (p1.getX() - p2.getX() + 20);
		}
		if (p1.getY() < p2.getY()) {
			y = (int) (p1.getY() - 10);
			h = (int) (p2.getY() - p1.getY() + 20);
		} else {
			y = (int) (p2.getY() - 10);
			h = (int) (p1.getY() - p2.getY() + 20);
		}
		
		
		//On redefini le point d'attraction en fonction de la largeur
	
		double yAttraction  = w * w / 10000 *  Math.log(w) ;
		this.setBounds(x, y, w , getParent().getHeight()); // +200 for curve more the link
		pBezier = new Point(getWidth() / 2, (int) (h - 10 + yAttraction) );
		
		//On trace une premiere ligne courbee
		ig.setStroke(new BasicStroke(9f));
		g.setColor(new Color(150, 150, 150));
		QuadCurve2D q = new QuadCurve2D.Double((int) p1.getX() - getX(),
				(int) p1.getY() - getY(), pBezier.x, pBezier.y, (int) p2.getX()
						- getX(), (int) p2.getY() - getY());
		ig.draw(q);

		//On trace une deuxieme ligne par dessus (purement esthetiqueà
		ig.setStroke(new BasicStroke(3f));
		g.setColor(new Color(110, 110, 110));
		q = new QuadCurve2D.Double((int) p1.getX() - getX(), (int) p1.getY()
				- getY(), pBezier.x, pBezier.y, (int) p2.getX() - getX(),
				(int) p2.getY() - getY());
		ig.draw(q);

		//On trace une troisième ligne hashee si il y a animation (purement esthetique)
		if (controller.outPortHasSignal()) {
			// animation
			g.setColor(new Color(90, 75, 90));
			ig.setStroke(new BasicStroke(3f, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, 10.0f, new float[] { 10f },
					animation));

			q = new QuadCurve2D.Double((int) p1.getX() - getX(),
					(int) p1.getY() - getY(), pBezier.x, pBezier.y,
					(int) p2.getX() - getX(), (int) p2.getY() - getY());
			ig.draw(q);
		}

		//On redefini le forme geometrique
		graphicLink = q;

	}



}
