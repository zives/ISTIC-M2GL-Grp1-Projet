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

public class PCable extends JPanel implements IPCable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1542461431128718349L;
	protected ICCable controller;
	protected Point p1;
	protected Point p2;
	QuadCurve2D graphicLink;
	private PCable self;
	private AWTEventListener mouseEvent;
	private float animation;
	private Timer timerAnimation;
	private Point pBezier;
	private boolean mouseMove; // TODO : utiliser ca si le cable gene pour le
								// relever


	public PCable(final CCable controller) {
		this.controller = controller;
		this.self = this;
		this.p1 = new Point(10, 10);
		this.p2 = new Point(20, 20);

		this.animation = 10;
		this.mouseMove = false;

		setLocation(0, 0);
		setSize(1, 1);
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));

	}

	@Override
	protected void finalize() throws Throwable {
		destruct();
		super.finalize();
	}

	public void destruct() {
		if (mouseEvent != null) {
			Toolkit.getDefaultToolkit().removeAWTEventListener(mouseEvent);
		}
	}

	public void cableConnected() {
		Toolkit.getDefaultToolkit().addAWTEventListener(
				mouseEvent = new AWTEventListener() {
					public void eventDispatched(AWTEvent e) {

						if (e instanceof MouseEvent) {
							MouseEvent m = (MouseEvent) e;
							if (m.getID() == MouseEvent.MOUSE_CLICKED) {

								if (m.getClickCount() == 2) {
									Point finalPoint = SwingUtilities.convertPoint(
											(Component) e.getSource(),
											m.getPoint(), self.getParent());
									if (graphicLink != null
											&& graphicLink.intersects(
													finalPoint.getX()
															- self.getX() - 16,
													finalPoint.getY()
															- self.getY() - 16,
													finalPoint.getX()
															- self.getX() + 16,
													finalPoint.getY()
															- self.getY() + 16)) {
										controller.disconnect();
										return;
									}
								}
							}

						}
					}
				}, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);

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

	public void setP1(int x, int y) {
		p1 = new Point(x, y);
		repaint();
		((JLayeredPane) this.getParent()).moveToFront(this);
	}

	public void setP1(IPPort port) {
		PPort p = (PPort) port;
		p1 = new Point(p.getX() + p.getParent().getX() + PPort.SIZE / 2
				+ PPort.getMARGIN(), p.getY() + p.getParent().getY()
				+ PPort.SIZE / 2);

		repaint();
		((JLayeredPane) this.getParent()).moveToFront(this);
	}

	public void setP2(int x, int y) {
		p2 = new Point(x, y);
		repaint();
		((JLayeredPane) this.getParent()).moveToFront(this);
	}

	public void setP2(IPPort port) {
		PPort p = (PPort) port;
		p2 = new Point(p.getX() + p.getParent().getX() + PPort.SIZE / 2
				+ PPort.getMARGIN(), p.getY() + p.getParent().getY()
				+ PPort.SIZE / 2);
		repaint();

		((JLayeredPane) this.getParent()).moveToFront(this);

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D ig = (Graphics2D) g;
		ig.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

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

		this.setBounds(x, y, w, h + 70); // +70 for curve more the line

		ig.setStroke(new BasicStroke(9f));
		g.setColor(new Color(150, 150, 150));

		if (!mouseMove) {
			pBezier = new Point(getWidth() / 2, getHeight());
		}

		// g.drawLine((int) p1.getX() - getX(), (int) p1.getY() - getY(),
		// (int) p2.getX() - getX(), (int) p2.getY() - getY());
		QuadCurve2D q = new QuadCurve2D.Double((int) p1.getX() - getX(),
				(int) p1.getY() - getY(), pBezier.x, pBezier.y, (int) p2.getX()
						- getX(), (int) p2.getY() - getY());
		ig.draw(q);

		ig.setStroke(new BasicStroke(3f));
		g.setColor(new Color(110, 110, 110));

		// g.drawLine((int) p1.getX() - getX(), (int) p1.getY() - getY(),
		// (int) p2.getX() - getX(), (int) p2.getY() - getY());
		q = new QuadCurve2D.Double((int) p1.getX() - getX(), (int) p1.getY()
				- getY(), pBezier.x, pBezier.y, (int) p2.getX() - getX(),
				(int) p2.getY() - getY());
		ig.draw(q);

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

		graphicLink = q;

	}



}
