package group1.project.synthlab.ihm.module.out;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.out.IOutModule;

/**
 * @author Groupe 1
 * Interface pour le controleur du module OUT
 */
public interface ICOutModule extends ICModule, IOutModule {
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.ICModule#getPresentation()
	 */
	public IPOutModule getPresentation();
}
