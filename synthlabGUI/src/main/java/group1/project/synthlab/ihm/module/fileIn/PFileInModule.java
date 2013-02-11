package group1.project.synthlab.ihm.module.fileIn;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

public class PFileInModule extends PModule implements IPFileInModule{

	protected ICFileInModule controller;
	JButton sampleFile;

	public PFileInModule(final ICFileInModule controller) {
		super(controller);
		this.controller = controller;
		this.setLayout(null);
		//Taille et couleur définie dans la super classe

		//Label et onoff boutons déjà rajoutés dans la super classe

		// Ports
		PPort pportOut = (PPort) (((ICOutPort) controller.getOutPort()).getPresentation());
		pportOut.setLocation(10, 220);

		// inputText
		sampleFile = new JButton("File");
		sampleFile.setLocation(50, 100);
		sampleFile.setSize(20, 20);
		sampleFile.setBorder(null);
		sampleFile.setPreferredSize(onOffButton.getSize());


		// Ajouts des composants
		add(pportOut);
		add(sampleFile);

		this.repaint();

		sampleFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				if (controller.getSample() == null) {
					controller.loadFile(new File(ClassLoader.getSystemResource("music.wav").getFile()));
				}
			}
		});
	}
}
