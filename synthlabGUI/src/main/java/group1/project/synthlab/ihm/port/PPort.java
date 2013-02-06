package group1.project.synthlab.ihm.port;

import group1.project.synthlab.exceptions.BadConnection;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

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
		this.setSize(new Dimension(52, 52));
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
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				repaint();
			}

			public void mouseEntered(MouseEvent ev) {
				//regarde si on peut poser un cable
				mouseEntered = true;
				controller.checkPutCable();
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				repaint();
			}

			public void mouseClicked(MouseEvent ev) {
				//Double clic suppression
				if (ev.getClickCount() == 2) {
					try {
						controller.removeCable();
					} catch (BadConnection e) {
						e.printStackTrace();
					}
				//Simple clic pose du cable
				} else if (ev.getClickCount() == 1) {
					try {
						controller.actionCable();
					} catch (BadConnection e) {
						e.printStackTrace();
					}
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
		else if (mouseEntered)
			g.setColor(mouseEnteredColor);
		else if (controller.isUsed())
			g.setColor(new Color(170, 170, 170));
	
		
		g.fillOval(MARGIN + 6, 6, SIZE - 12, SIZE - 12);
	
		ig.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		Font font = new Font("Arial", Font.ITALIC, 9);
		g.setFont(font);
		g.setColor(Color.WHITE);
		FontMetrics metrics = g.getFontMetrics(font);
		String text = controller.getLabel();
		if (text.length() > 14)
			text = text.substring(0, 14);
		int wLabel = metrics.stringWidth(text);
		g.drawString(text, (getWidth() - wLabel) / 2, 42);
	}


	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.port.IPPort#setForbidden()
	 */
	public void setForbidden() {
		mouseEnteredColor = new Color(140, 40, 40);
		
	}


	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.port.IPPort#setAllowed()
	 */
	public void setAllowed() {
		mouseEnteredColor = new Color(150, 150, 150);
	}


	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.port.IPPort#setAllowedToDelete()
	 */
	public void setAllowedToDelete() {
		mouseEnteredColor = new Color(50, 0, 0);
	}



}
