package group1.project.synthlab.ihm.module.eq;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.eq.IEQModule;

public interface ICEQModule extends ICModule, IEQModule {
	public IPEQModule getPresentation();
}
