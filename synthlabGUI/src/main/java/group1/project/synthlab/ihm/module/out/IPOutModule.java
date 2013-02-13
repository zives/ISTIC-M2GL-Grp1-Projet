package group1.project.synthlab.ihm.module.out;

import group1.project.synthlab.ihm.module.IPModule;

import java.awt.Point;

public interface IPOutModule extends IPModule {

	public void updateSlider();

	public void updateDistribution();

	public Point getLocation();

	public void updateLocation(double x, double y);

	
}
