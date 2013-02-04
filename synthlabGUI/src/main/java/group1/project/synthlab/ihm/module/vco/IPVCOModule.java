package group1.project.synthlab.ihm.module.vco;

import group1.project.synthlab.ihm.module.IPModule;
import group1.project.synthlab.unitExtensions.FilterAmplitude.IFilterAmplitudeObserver;

public interface IPVCOModule extends IPModule,  IFilterAmplitudeObserver {
	public void setSlidersEnabled(boolean value);
}
