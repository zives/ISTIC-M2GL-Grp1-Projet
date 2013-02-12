package group1.project.synthlab.ihm.module.out;

import java.awt.Point;

import javax.swing.JComponent;

import group1.project.synthlab.ihm.module.IPModule;

public interface IPOutModule extends IPModule {

	public void updateSlider();

	public void updateDistribution();

	public Point getLocation();

	
}
