package group1.project.synthlab.ihm.module.out;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class POutModule extends PModule implements IPOutModule {

	private static final long serialVersionUID = 9202805048987933945L;
	
	protected ICOutModule controller;
	
	public POutModule(ICOutModule controller) {
		this.controller = controller;
		this.setLayout(null);
		this.setBackground(new Color(70,70,70));
		this.setSize(300, 300);
		this.setPreferredSize(this.getSize());
		this.setMaximumSize(this.getSize());
		
		//Label
		JLabel label = new JLabel(controller.getName());
		label.setForeground(Color.GRAY);
		label.setOpaque(false);
		label.setLocation(127, 137);
		
		//Ports
		PPort pportLeft = (PPort) (((ICInPort) controller.getLeftPort()).getPresentation());
		pportLeft.setLocation(10, 220);
		PPort pportRight = (PPort) (((ICInPort) controller.getRightPort()).getPresentation());
		pportRight.setLocation(70, 220);
		
		//Ajouts des composants
		add(label);
		add(pportLeft);
		add(pportRight);
		
	}
	
	
	@Override
	public void paint(Graphics g) {	
		super.paint(g);
		Graphics2D gi = (Graphics2D) g;
		gi.setStroke(new BasicStroke(8f));
		g.setColor(new Color(50,50,50));
		g.drawRect(5, 5, getWidth() - 10, getHeight() - 10);
		
	}
	
	
	public static void main(String[] args) {
		CFactory factory = new CFactory();
		COutModule module = (COutModule) factory.createOutModule();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add((Component) module.getPresentation());
		frame.pack();
		frame.setVisible(true);
		
	}

}


