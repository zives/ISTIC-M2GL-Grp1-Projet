package group1.project.synthlab.ihm.module.vcf.hp;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.vcf.hp.IVCFHPModule;

public interface ICVCFHPModule extends ICModule, IVCFHPModule {
	public IPVCFHPModule getPresentation();

}