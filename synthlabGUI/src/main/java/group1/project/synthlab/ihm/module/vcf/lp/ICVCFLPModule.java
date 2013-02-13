package group1.project.synthlab.ihm.module.vcf.lp;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.vcf.lp.IVCFLPModule;

public interface ICVCFLPModule extends ICModule, IVCFLPModule {
	public IPVCFLPModule getPresentation();

}