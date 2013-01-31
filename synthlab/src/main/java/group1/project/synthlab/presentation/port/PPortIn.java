package group1.project.synthlab.presentation.port;

import group1.project.synthlab.control.port.in.IInPort;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class PPortIn extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private Image img;
	private IInPort control;

	public PPortIn(IInPort control) {
		this.control = control;
		this.img = new ImageIcon("resources/images/port.png").getImage();

		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));    
		this.setSize(size);
		this.setPreferredSize(getSize());
		this.setOpaque(false);

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = (getWidth() - img.getWidth(this))/2;
		int y = (getHeight() - img.getHeight(this))/2;
		g.drawImage(img, x, y, null);
	}

	public void setControl(IInPort control) {
		this.control = control;
	}

	public IInPort getControl() {
		return control;
	}

}
