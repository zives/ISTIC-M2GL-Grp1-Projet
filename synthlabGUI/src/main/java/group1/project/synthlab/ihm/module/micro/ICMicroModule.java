package group1.project.synthlab.ihm.module.micro;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.micro.IMicroModule;

/**
 * @author Groupe 1
 * Interface pour le controleur du module Micro
 */
public interface ICMicroModule extends ICModule, IMicroModule {
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.ICModule#getPresentation()
	 */
	public IPMicroModule getPresentation();
}
