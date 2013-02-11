package group1.project.synthlab.ihm.module.vco.piano;

import group1.project.synthlab.ihm.module.vco.ICVCOModule;
import group1.project.synthlab.module.vco.piano.IVCOPianoModule;

public interface ICVCOPianoModule extends IVCOPianoModule, ICVCOModule {
	public void play(int note, int octatve, boolean sharp);
	public void stopPlay();
	public void changeoctave(int value);
}
