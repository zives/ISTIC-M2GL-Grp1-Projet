package group1.project.synthlab.ihm.module.vcf.lp;

import group1.project.synthlab.ihm.module.vcf.ICVCFModule;
import group1.project.synthlab.module.vcf.lp.IVCFLPModule;

public interface ICVCFLPModule extends ICVCFModule, IVCFLPModule {
	public IPVCFLPModule getPresentation();

}