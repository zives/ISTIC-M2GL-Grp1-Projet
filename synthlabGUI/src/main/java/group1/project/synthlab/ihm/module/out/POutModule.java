package group1.project.synthlab.ihm.module.out;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;

import java.awt.Component;

import javax.swing.JFrame;

public class POutModule extends PModule implements IPOutModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected ICOutModule controller;

	public POutModule(ICOutModule controller) {
		super(controller);
		this.controller = controller;
		this.setLayout(null);
		//Taille et couleur définie dans la super classe
		
		//Label et onoff boutons déjà rajoutés dans la super classe

		// Ports
		PPort pportLeft = (PPort) (((ICInPort) controller.getLeftPort())
				.getPresentation());
		pportLeft.setLocation(10, 220);
		PPort pportRight = (PPort) (((ICInPort) controller.getRightPort())
				.getPresentation());
		pportRight.setLocation(70, 220);

		// Ajouts des composants
		add(pportLeft);
		add(pportRight);

	}






	

}
