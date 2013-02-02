package group1.project.synthlab.ihm.port;

import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.port.in.IInPort;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.IconView;

public class PPort extends JPanel implements IPPort {
	private static final long serialVersionUID = -7576041067842211961L;
	public final static int SIZE = 32;
	protected static int MARGIN = 1;
	protected ICPort controller;
	protected boolean mouseEntered;
	protected Color mouseEnteredColor;

	public PPort(final ICPort controller) {
		this.setOpaque(false);
		this.setBackground(new Color(0, 0, 0, 0));
		this.setSize(new Dimension(42, 42));
		this.setPreferredSize(getSize());
		this.mouseEntered = false;
		this.MARGIN = (getWidth() - SIZE) / 2;
		this.controller = controller;
		this.mouseEnteredColor = new Color(0, 0, 0);
		
		this.addMouseListener(new MouseListener() {


			public void mouseReleased(MouseEvent ev) {
			}

			public void mousePressed(MouseEvent ev) {
			}

			public void mouseExited(MouseEvent ev) {
				mouseEntered = false;
				repaint();
			}

			public void mouseEntered(MouseEvent ev) {
				mouseEntered = true;
				controller.checkPutCable();
				repaint();
			}

			public void mouseClicked(MouseEvent ev) {
				try {
					controller.actionCable();
				} catch (BadConnection e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	public static int getMARGIN() {
		return MARGIN;
	}


	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D ig = (Graphics2D) g;
		ig.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.DARK_GRAY);
		g.fillOval(MARGIN + 2, 2, SIZE - 4, SIZE - 4);
		if (!controller.isUsed() && !mouseEntered)
			g.setColor(Color.BLACK);
		else if (controller.isUsed())
			g.setColor(new Color(170, 170, 170));
		else if (mouseEntered)
			g.setColor(mouseEnteredColor);
		g.fillOval(MARGIN + 6, 6, SIZE - 12, SIZE - 12);

		ig.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		Font font = new Font("Arial", Font.ITALIC, 9);
		g.setFont(font);
		g.setColor(Color.WHITE);
		FontMetrics metrics = g.getFontMetrics(font);
		String text = controller.getLabel();
		if (text.length() > 10)
			text = text.substring(0, 10);
		int wLabel = metrics.stringWidth(text);
		g.drawString(text, (getWidth() - wLabel) / 2, 42);
	}


	public void setForbidden() {
		mouseEnteredColor = new Color(200, 50, 50);
		
	}


	public void setAllowed() {
		mouseEnteredColor = new Color(170, 170, 170);
		
	}



}
