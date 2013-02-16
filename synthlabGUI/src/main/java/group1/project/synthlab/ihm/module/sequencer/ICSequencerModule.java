package group1.project.synthlab.ihm.module.sequencer;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.sequencer.ISequencerModule;

/**
 * @author Groupe 1
 * Interface pour le controleur du sequenceur
 */
public interface ICSequencerModule extends ICModule, ISequencerModule {
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.ICModule#getPresentation()
	 */
	public IPSequencerModule getPresentation();

}
