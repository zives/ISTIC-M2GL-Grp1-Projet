package group1.project.synthlab.ihm.module.vco.piano;

import java.awt.event.MouseEvent;

import group1.project.synthlab.ihm.module.vco.ICVCOModule;

public interface ICVCOPianoModule extends ICVCOModule {
	public void play(int note, int octatve, boolean sharp);
	public void stopPlay();
	public void changeoctave(int value);
}
