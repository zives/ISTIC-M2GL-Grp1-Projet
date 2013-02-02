package group1.project.synthlab.ihm.module.vco;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class PVCOModule extends PModule implements IPVCOModule {

	private static final long serialVersionUID = 9202805048987933945L;
	
	protected ICVCOModule controller;
	
	public PVCOModule(ICVCOModule controller) {
		this.controller = controller;
		this.setLayout(null);
		this.setBackground(new Color(70,70,70));
		this.setSize(300, 300);
		this.setPreferredSize(this.getSize());
		this.setMaximumSize(this.getSize());
		
		//Enregitrement du module auprès des cables
		register((IPModuleObserver) controller.getFm().getCable());
		register((IPModuleObserver) controller.getOutSine().getCable());
		register((IPModuleObserver) controller.getOutTriangle().getCable());
		register((IPModuleObserver) controller.getOutSquare().getCable());
		
		//Label
		JLabel label = new JLabel(controller.getName());
		label.setForeground(Color.GRAY);
		label.setOpaque(false);
		label.setLocation(127, 137);
		
		//Ports
		PPort pportFM = (PPort) (((ICInPort) controller.getFm()).getPresentation());
		pportFM.setLocation(10, 220);
		PPort pportSin = (PPort) (((ICOutPort) controller.getOutSine()).getPresentation());
		pportSin.setLocation(70, 220);
		PPort pportTri = (PPort) (((ICOutPort) controller.getOutTriangle()).getPresentation());
		pportTri.setLocation(130, 220);
		PPort pportSqu = (PPort) (((ICOutPort) controller.getOutSquare()).getPresentation());
		pportSqu.setLocation(200, 220);
		
		
		//Ajouts des composants
		add(label);
		add(pportFM);
		add(pportSin);
		add(pportTri);
		add(pportSqu);
		
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
		CVCOModule module = (CVCOModule) factory.createVCOModule();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add((Component) module.getPresentation());
		frame.pack();
		frame.setVisible(true);
		
	}

}


