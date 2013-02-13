package group1.project.synthlab.ihm.module.sequencer;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.sequencer.ISequencerModule;

public interface ICSequencer extends ICModule, ISequencerModule {
	public IPSequencer getPresentation();

}
