package group1.project.synthlab.ihm.module.vcf.lp;

import group1.project.synthlab.ihm.module.vcf.hp.ICVCFHPModule;
import group1.project.synthlab.module.vcf.lp.IVCFLPModule;

public interface ICVCFLPModule extends ICVCFHPModule, IVCFLPModule {
	public IPVCFLPModule getPresentation();

}