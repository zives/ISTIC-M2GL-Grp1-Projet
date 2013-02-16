package group1.project.synthlab.ihm.module.eq;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.eq.IEQModule;

/**
 * @author Groupe 1
 * Interface pour le controleur du module EQ
 */
public interface ICEQModule extends ICModule, IEQModule {
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.ICModule#getPresentation()
	 */
	public IPEQModule getPresentation();
}
