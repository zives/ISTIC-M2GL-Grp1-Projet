package group1.project.synthlab.ihm.module.vco;

import java.awt.Point;

import group1.project.synthlab.ihm.module.IPModule;
import group1.project.synthlab.unitExtensions.filterSupervisor.IFilterAmplitudeObserver;

public interface IPVCOModule extends IPModule,  IFilterAmplitudeObserver {
	public void setSlidersEnabled(boolean value);

	public void updateCoarseAdjustment(int coarseAdjustment);

	public void updateFineAdjustment(double fineAdjustment);
	
	public Point getLocation();

	public void updateLocation(double x, double y);
}
