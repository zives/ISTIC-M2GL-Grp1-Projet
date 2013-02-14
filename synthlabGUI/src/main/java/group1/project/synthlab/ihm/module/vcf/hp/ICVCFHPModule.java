package group1.project.synthlab.ihm.module.vcf.hp;

import group1.project.synthlab.ihm.module.vcf.ICVCFModule;
import group1.project.synthlab.module.vcf.hp.IVCFHPModule;

public interface ICVCFHPModule extends ICVCFModule, IVCFHPModule {
	public IPVCFHPModule getPresentation();

}