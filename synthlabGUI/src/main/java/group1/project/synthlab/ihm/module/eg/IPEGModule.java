package group1.project.synthlab.ihm.module.eg;

import java.awt.Point;

import javax.swing.JComponent;

import group1.project.synthlab.ihm.module.IPModule;
import group1.project.synthlab.unitExtensions.filterSupervisor.IFilterAmplitudeObserver;

public interface IPEGModule extends IPModule {
	public void setSlidersEnabled(boolean value);

	public Point getLocation();

	public void updateLocation(double x, double y);

	public void updateAttackSlider(double attack);

	public void updateDecaySlider(double decay);

	public void updateReleaseSlider(double release);

	public void updateHoldSlider(double hold);

	public void updateSustainSlider(double sustain);
}
