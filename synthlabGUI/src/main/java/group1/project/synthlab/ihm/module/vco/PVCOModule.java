package group1.project.synthlab.ihm.module.vco;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JSlider;

public class PVCOModule extends PModule implements IPVCOModule {

	private static final long serialVersionUID = 9202805048987933945L;
	
	protected ICVCOModule controller;
	
	public PVCOModule(ICVCOModule controller) {
		super(controller);
		this.controller = controller;
		//Taille et couleur définie dans la super classe
		
		
		//Label et onoff boutons déjà rajoutés dans la super classe
			
		//Sliders
		JSlider octaveSlider = new JSlider();
		octaveSlider.setMaximum(9);
		octaveSlider.setMinimum(0);
		octaveSlider.setValue(controller.getCoarseAdjustment());
		octaveSlider.setOrientation(JSlider.HORIZONTAL);
		octaveSlider.setSize(200, 40);
		octaveSlider.setMajorTickSpacing(1);
		octaveSlider.setPaintTicks(true);
		octaveSlider.setPaintLabels(true);
		octaveSlider.setFont(new Font("Arial", 0, 8));
		octaveSlider.setForeground(Color.LIGHT_GRAY);
		octaveSlider.setPreferredSize(octaveSlider.getSize());
		octaveSlider.setOpaque(false);
		octaveSlider.setLocation(getWidth() / 2 - octaveSlider.getWidth() / 2, 100);
		
		//Ports
		PPort pportFM = (PPort) (((ICInPort) controller.getFm()).getPresentation());
		pportFM.setLocation(10, 220);
		PPort pportSin = (PPort) (((ICOutPort) controller.getOutSine()).getPresentation());
		pportSin.setLocation(70, 220);
		PPort pportTri = (PPort) (((ICOutPort) controller.getOutTriangle()).getPresentation());
		pportTri.setLocation(130, 220);
		PPort pportSqu = (PPort) (((ICOutPort) controller.getOutSquare()).getPresentation());
		pportSqu.setLocation(190, 220);
		
		
		//Ajouts des composants
		add(pportFM);
		add(pportSin);
		add(pportTri);
		add(pportSqu);
		add(octaveSlider);
		
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


	public void reregisterCables() {
		//Enregitrement du module auprès des cables
		register((IPModuleObserver) controller.getFm().getCable());
		register((IPModuleObserver) controller.getOutSine().getCable());
		register((IPModuleObserver) controller.getOutTriangle().getCable());
		register((IPModuleObserver) controller.getOutSquare().getCable());
		
	}

}


