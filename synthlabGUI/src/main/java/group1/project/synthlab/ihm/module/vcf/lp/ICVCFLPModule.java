package group1.project.synthlab.ihm.module.vcf.lp;

import group1.project.synthlab.ihm.module.vcf.ICVCFModule;
import group1.project.synthlab.module.vcf.lp.IVCFLPModule;

/**
 * @author Groupe 1
 * Interface pour les controleurs VCF LP
 */
public interface ICVCFLPModule extends ICVCFModule, IVCFLPModule {
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.vcf.ICVCFModule#getPresentation()
	 */
	public IPVCFLPModule getPresentation();

}