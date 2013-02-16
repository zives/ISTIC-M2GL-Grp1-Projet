package group1.project.synthlab.ihm.module.vcf;

import group1.project.synthlab.ihm.module.IPModule;

/**
 * @author Groupe 1
 * Interface generale pour les presentations des modules VCF
 */
public interface IPVCFModule extends IPModule{
	
	/**
	 * Defini si les sliders peuvent etre accessibles
	 * @param value
	 */
	public void setSlidersEnabled(boolean value);
}
