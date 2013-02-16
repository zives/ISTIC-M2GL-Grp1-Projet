package group1.project.synthlab.ihm.module.vco;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.vco.IVCOModule;

/**
 * @author Groupe 1
 * Interface pour le controleur du module VCO
 */
public interface ICVCOModule extends ICModule, IVCOModule {
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.ICModule#getPresentation()
	 */
	public IPVCOModule getPresentation();
}
