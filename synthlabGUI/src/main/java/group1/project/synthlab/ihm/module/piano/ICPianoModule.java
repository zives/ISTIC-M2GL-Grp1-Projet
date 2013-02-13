package group1.project.synthlab.ihm.module.piano;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.piano.IPianoModule;

public interface ICPianoModule extends IPianoModule, ICModule {
	public void playFromPresentation(int note, int octatve, boolean sharp);
	public void stopPlay();
	public void changeoctave(int value);
	int getOctaveStart();
}
