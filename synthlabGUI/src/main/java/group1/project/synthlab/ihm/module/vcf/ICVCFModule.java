package group1.project.synthlab.ihm.module.vcf;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.vcf.IVCFModule;

/**
 * @author Groupe 1
 * Interface generale pour les controleur des modules VCF
 */
public interface ICVCFModule extends ICModule, IVCFModule {
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.ICModule#getPresentation()
	 */
	public IPVCFModule getPresentation();

}