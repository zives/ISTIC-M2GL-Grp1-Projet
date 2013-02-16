package group1.project.synthlab.ihm.module.eqView;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.eqView.IEQViewModule;

/**
 * @author Groupe 1
 * Interface pour le controleur du module EQView
 */
public interface ICEQViewModule extends ICModule, IEQViewModule {
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.ICModule#getPresentation()
	 */
	public IPEQViewModule getPresentation();
}
