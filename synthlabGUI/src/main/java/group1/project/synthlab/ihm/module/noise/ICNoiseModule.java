package group1.project.synthlab.ihm.module.noise;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.noise.INoiseModule;

/**
 * @author Groupe 1
 * Interface pour le controleur du module Noise
 */
public interface ICNoiseModule extends ICModule, INoiseModule {
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.ICModule#getPresentation()
	 */
	public IPNoiseModule getPresentation();
}
