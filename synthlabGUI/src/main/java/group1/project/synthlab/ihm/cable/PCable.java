package group1.project.synthlab.ihm.cable;

import group1.project.synthlab.ihm.port.IPPort;
import group1.project.synthlab.ihm.port.PPort;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class PCable extends JPanel implements IPCable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1542461431128718349L;
	protected ICCable controller;
	protected Point p1;
	protected Point p2;

	public PCable(CCable controller) {
		this.controller = controller;

		this.p1 = new Point(10, 10);
		this.p2 = new Point(20, 20);

		setLocation(0, 0);
		setSize(1, 1);
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));

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

		this.setBounds(new Rectangle(x, y, w, h));

		ig.setStroke(new BasicStroke(9f));
		g.setColor(new Color(100, 100, 100));
		g.drawLine((int) p1.getX() - getX(), (int) p1.getY() - getY(),
				(int) p2.getX() - getX(), (int) p2.getY() - getY());
		ig.setStroke(new BasicStroke(3f));
		g.setColor(new Color(200, 200, 200));
		g.drawLine((int) p1.getX() - getX(), (int) p1.getY() - getY(),
				(int) p2.getX() - getX(), (int) p2.getY() - getY());
		
		
	}

}
