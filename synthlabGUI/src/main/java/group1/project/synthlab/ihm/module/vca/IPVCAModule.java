package group1.project.synthlab.ihm.module.vca;

import group1.project.synthlab.ihm.module.IPModule;

/**
 * @author Groupe 1
 * Interface pour la presentation du module VCA
 */
public interface IPVCAModule extends IPModule{
	
	
	/**
	 * Indique si oui ou non les sliders de la presentation doivent etre accessibles
	 * @param value
	 */
	public void setSlidersEnabled(boolean value);

}
