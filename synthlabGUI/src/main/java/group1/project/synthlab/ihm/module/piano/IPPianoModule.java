package group1.project.synthlab.ihm.module.piano;

import group1.project.synthlab.ihm.module.IPModule;

import java.awt.event.MouseEvent;

public interface IPPianoModule extends IPModule {
	public void mouseEvent(MouseEvent me);

	public void updateSliderOctave(int position);

	public void updateLocation(double x, double y);
}
