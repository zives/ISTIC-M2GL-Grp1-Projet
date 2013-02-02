package group1.project.synthlab.ihm.module.vco;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.vco.IVCOModule;

public interface ICVCOModule extends ICModule, IVCOModule {
	public IPVCOModule getPresentation();
}
