package group1.project.synthlab.ihm.module.vcf;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.vcf.IVCFModule;

public interface ICVCFModule extends ICModule, IVCFModule {
	public IPVCFModule getPresentation();

}