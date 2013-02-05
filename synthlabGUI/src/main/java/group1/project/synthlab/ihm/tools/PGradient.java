package group1.project.synthlab.ihm.tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PGradient extends JPanel {

	protected Color color1 ;
	protected Color color2;
	protected float cut; //entre 0 et 1 

	public PGradient(Color color1, Color color2, float cut) {
		this.color1 = color1;
		this.color2 = color2;
		this.cut = cut;
	}

	public Color getColor1() {
		return color1;
	}
	public void setColor1(Color color1) {
		this.color1 = color1;
	}
	public Color getColor2() {
		return color2;
	}
	public void setColor2(Color color2) {
		this.color2 = color2;
	}
	private static final long serialVersionUID = 1L;
	public void init() {
		setBackground(Color.gray);
	}

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		Graphics2D g2d = (Graphics2D) graphics;

		g2d.setPaint(new GradientPaint(0, 0, getColor1(), 0, cut*getHeight(), getColor2(), false));
		Rectangle2D.Double rectangle = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
		g2d.fill(rectangle);
		g2d.setPaint(new GradientPaint(0,  cut*getHeight(), getColor2(), 0, getHeight() - cut*getHeight(), getColor1(), false));
		rectangle = new Rectangle2D.Double(0, cut*getHeight(), getWidth(), getHeight() - cut*getHeight());
		g2d.fill(rectangle);
	

	}



}
