package group1.project.synthlab.ihm.module.noise;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

/**
 * @author Groupe 1
 * Presentation du module Noise
 */
public class PNoiseModule extends PModule implements IPNoiseModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected transient ICNoiseModule controller;

	public PNoiseModule(final ICNoiseModule controller) {
		super(controller);
		this.controller = controller;
		this.setLayout(null);
		// Taille et couleur d�finie dans la super classe

		// Label et onoff boutons d�j� rajout�s dans la super classe

		// Ports
		PPort pportOutWhite = (PPort) (((ICOutPort) controller.getOutWhite())
				.getPresentation());
		pportOutWhite.setLocation(30, 60);
		PPort pportOutPink = (PPort) (((ICOutPort) controller.getOutPink())
				.getPresentation());
		pportOutPink.setLocation(90, 60);
		PPort pportOutBrownian = (PPort) (((ICOutPort) controller.getOutBrownian())
				.getPresentation());
		pportOutBrownian.setLocation(150, 60);
				
		// Ajout des composants
		add(pportOutWhite);
		add(pportOutPink);
		add(pportOutBrownian);
		
		setSize(getWidth() - 60, getHeight() - 150);
		setPreferredSize(getSize());
		onOffButton.setLocation(getWidth() - onOffButton.getWidth() - 13, 13);
		removeButton.setLocation(getWidth() - removeButton.getWidth()
				- onOffButton.getWidth() - 15, 13);

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.PModule#updatePresentation()
	 */
	@Override
	public void updatePresentation() {
		super.updatePresentation();
	
	}

}