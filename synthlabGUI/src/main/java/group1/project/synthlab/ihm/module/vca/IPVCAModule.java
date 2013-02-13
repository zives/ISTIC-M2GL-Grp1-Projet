package group1.project.synthlab.ihm.module.vca;

import java.awt.Point;

import javax.swing.JComponent;

import group1.project.synthlab.ihm.module.IPModule;

public interface IPVCAModule extends IPModule{
	public void setSlidersEnabled(boolean value);

	public Point getLocation();

	public void updateLocation(double x, double y);

	public void updateGainSlider();
}
