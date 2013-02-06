package group1.project.synthlab.ihm.module.vca;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.vca.IVCAModule;

public interface ICVCAModule extends ICModule, IVCAModule {
	public IPVCAModule getPresentation();

}
