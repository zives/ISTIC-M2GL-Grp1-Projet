package group1.project.synthlab.ihm.port.in;

import group1.project.synthlab.ihm.factory.ControllerFactory;
import group1.project.synthlab.ihm.port.ICPort;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.out.COutPort;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PInPort extends PPort implements IPInPort {

	private static final long serialVersionUID = 30814597301574881L;

	public PInPort(ICPort controller) {
		super(controller);
	}

	@Override
	public void paint(Graphics g) {		
		g.setColor(new Color(70, 160, 70));
		Graphics2D ig = (Graphics2D) g;
		ig.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillOval(MARGIN, 0, SIZE, SIZE);
		super.paint(g);
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		CInPort port = (CInPort)(ControllerFactory.getInstance().createInPort("test", null));
		panel.add((Component) port.getPresentation());
		panel.setBackground(Color.BLACK);
		frame.add(panel);
		
		frame.pack();
		frame.setVisible(true);
	}

	public void refresh() {
		repaint();
		
	}
}
