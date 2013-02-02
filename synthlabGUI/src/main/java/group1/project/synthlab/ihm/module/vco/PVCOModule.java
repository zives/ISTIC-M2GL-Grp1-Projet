package group1.project.synthlab.ihm.module.vco;

import javax.swing.JPanel;

public class PVCOModule extends JPanel implements IPVCOModule {

	private static final long serialVersionUID = 9202805048987933945L;
	
	protected ICVCOModule controller;
	
	public PVCOModule(ICVCOModule controller) {
		this.controller = controller;
	}

}
