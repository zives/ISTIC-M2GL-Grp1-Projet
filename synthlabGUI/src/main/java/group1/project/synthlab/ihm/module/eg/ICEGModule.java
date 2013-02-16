package group1.project.synthlab.ihm.module.eg;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.eg.IEGModule;

/**
 * @author Groupe 1
 * Interface pour le controleur du module EG
 */
public interface ICEGModule extends ICModule, IEGModule {
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.ICModule#getPresentation()
	 */
	public IPEGModule getPresentation();
}
