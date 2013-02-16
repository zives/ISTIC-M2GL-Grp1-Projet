package group1.project.synthlab.ihm.module.vca;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.vca.IVCAModule;

/**
 * @author Groupe 1
 * Interface pour le controleur du module VCA
 */
public interface ICVCAModule extends ICModule, IVCAModule {
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.ICModule#getPresentation()
	 */
	public IPVCAModule getPresentation();

}
