package group1.project.synthlab.ihm.module.eg;

import group1.project.synthlab.ihm.module.IPModule;
import group1.project.synthlab.unitExtensions.FilterAmplitude.IFilterAmplitudeObserver;

public interface IPEGModule extends IPModule,  IFilterAmplitudeObserver {
	public void setSlidersEnabled(boolean value);
}
