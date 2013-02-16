package group1.project.synthlab.ihm.module.multiplexer;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.multiplexer.IMultiplexerModule;

/**
 * @author Groupe 1
 * Interface pour le controleur du module Multiplexer
 */
public interface ICMultiplexerModule extends ICModule, IMultiplexerModule {
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.ICModule#getPresentation()
	 */
	public IPMultiplexerModule getPresentation();
}
