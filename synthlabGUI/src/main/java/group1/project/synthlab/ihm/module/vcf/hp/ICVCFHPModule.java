package group1.project.synthlab.ihm.module.vcf.hp;

import group1.project.synthlab.ihm.module.vcf.ICVCFModule;
import group1.project.synthlab.module.vcf.hp.IVCFHPModule;

/**
 * @author Groupe 1
 * Interface pour le controleur du module VCF HP
 */
public interface ICVCFHPModule extends ICVCFModule, IVCFHPModule {
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.vcf.ICVCFModule#getPresentation()
	 */
	public IPVCFHPModule getPresentation();

}