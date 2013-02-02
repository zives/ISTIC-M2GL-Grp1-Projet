package group1.project.synthlab.ihm.module.vco;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PVCOModule extends JPanel implements IPVCOModule {

	private static final long serialVersionUID = 9202805048987933945L;
	
	protected ICVCOModule controller;
	
	public PVCOModule(ICVCOModule controller) {
		this.controller = controller;
		this.setBackground(new Color(70,70,70));
		this.setSize(200, 300);
		this.setPreferredSize(this.getSize());
		
		//Label
		JLabel label = new JLabel(controller.getName());
		label.setForeground(Color.GRAY);
		label.setOpaque(false);
		label.setLocation(17, 7);
		
		//Ports
		PPort pportFM = (PPort) (((ICInPort) controller.getFm()).getPresentation());
		pportFM.setLocation(10, 250);
		
		
		//Ajouts des composants
		add(label);
		
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(new Color(50,50,50));
		g.fillRect(5, 5, getWidth() - 10, getHeight() - 10);
		
	}
	
	
	public static void main(String[] args) {
		CFactory factory = new CFactory();
		CVCOModule module = (CVCOModule) factory.createVCOModule();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add((Component) module.getPresentation());
		frame.pack();
		frame.setVisible(true);
		
	}

}


