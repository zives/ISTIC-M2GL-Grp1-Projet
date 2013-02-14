package group1.project.synthlab.ihm.module.vco;

import group1.project.synthlab.ihm.module.IPModule;
import group1.project.synthlab.unitExtension.filter.filterSupervisor.IFilterAmplitudeObserver;

import java.awt.Point;

public interface IPVCOModule extends IPModule,  IFilterAmplitudeObserver {
	public void setSlidersEnabled(boolean value);
	

}
