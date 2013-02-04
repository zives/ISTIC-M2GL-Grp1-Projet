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
	protected Color color3;
	protected float cut1; //entre 0 et 1 
	protected float cut2;
	protected float middleCut;
	
	public PGradient(Color color1, Color color2, Color color3, float cut1, float middleCut, float cut2) {
		this.color1 = color1;
		this.color2 = color2;
		this.color3 = color3;
		this.cut1 = cut1;
		this.middleCut = middleCut;
		this.cut2 = cut2;
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
	public Color getColor3() {
		return color3;
	}
	public void setColor3(Color color3) {
		this.color3 = color3;
	}

		private static final long serialVersionUID = 1L;
		public void init() {
			setBackground(Color.gray);
		}
		
		@Override
		public void paint(Graphics graphics) {
			super.paint(graphics);
			Graphics2D g2d = (Graphics2D) graphics;
//			  g2d.setPaint(new GradientPaint(0, 0, getColor1(), 0,30, getColor2(), false));
//			  Rectangle2D.Double rectangle = new Rectangle2D.Double(0,0,getWidth(),getHeight()/2);
//			  g2d.fill(rectangle);
//			  g2d.setPaint(new GradientPaint(0, getHeight()/4, getColor2(), 0, getHeight(), getColor3(), false));
//			 rectangle = new Rectangle2D.Double(0,getHeight()/4,getWidth(),getHeight()*0.75);
			
			g2d.setPaint(new GradientPaint(0, 0, getColor1(), 0, cut1*getHeight(), getColor2(), false));
			  Rectangle2D.Double rectangle = new Rectangle2D.Double(0,0,getWidth(),getHeight()*middleCut);
			  g2d.fill(rectangle);
			  g2d.setPaint(new GradientPaint(0, getHeight()*middleCut, getColor2(), 0, getHeight(), getColor3(), false));
			 rectangle = new Rectangle2D.Double(0,((1-cut2)*(getHeight()*(1-middleCut))/getHeight()),getWidth(),getHeight()*(1-middleCut));
			  g2d.fill(rectangle);
			
		}
	
		public static void main(String args[]) {

			JFrame f = new JFrame("");
			PGradient gradient = new PGradient(Color.darkGray, Color.white, Color.darkGray, 0.2f, 0.4f, 0.7f);
			 f.getContentPane().add("Center", gradient);
			    gradient.init();
			    f.pack();
			    f.setSize(new Dimension(300, 300));
			    f.show();
			
		}

	
}
