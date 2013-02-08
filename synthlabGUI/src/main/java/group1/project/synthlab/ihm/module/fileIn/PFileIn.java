package group1.project.synthlab.ihm.module.fileIn;

import java.awt.TextField;
import java.io.File;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

public class PFileIn extends PModule implements IPFileIn{

	private ICFileIn controller;

	public PFileIn(ICFileIn controller) {
		super(controller);
		this.controller = controller;
		this.setLayout(null);
		//Taille et couleur définie dans la super classe

		//Label et onoff boutons déjà rajoutés dans la super classe

		// Ports
		PPort pportLeft = (PPort) (((ICOutPort) controller.getLeftPort()).getPresentation());
		pportLeft.setLocation(10, 220);
		PPort pportRight = (PPort) (((ICOutPort) controller.getRightPort()).getPresentation());
		pportRight.setLocation(70, 220);
		PPort pportMono = (PPort) (((ICOutPort) controller.getMonoPort()).getPresentation());
		pportMono.setLocation(130, 220);
		
		// inputText
		TextField sampleFile = new TextField();
		sampleFile.setLocation(80, 100);
		
		// Ajouts des composants
		add(pportLeft);
		add(pportRight);
		add(pportMono);
		add(sampleFile);
		
		this.repaint();
		
		controller.loadFile(new File(ClassLoader.getSystemResource(sampleFile.getText()).getFile()));
	}
}
