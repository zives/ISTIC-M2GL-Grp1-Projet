package group1.project.synthlab.ihm.module.vco;

import group1.project.synthlab.ihm.module.IPModule;
import group1.project.synthlab.unitExtension.filter.filterSupervisor.IFilterAmplitudeObserver;

/**
 * @author Groupe 1
 * Interface pour la presentation du module VCO
 */
public interface IPVCOModule extends IPModule,  IFilterAmplitudeObserver {
	
	/**
	 * Indique si les sliders doivent etre actives ou desactives
	 * @param value
	 */
	
	public void setSlidersEnabled(boolean value);
	

}
