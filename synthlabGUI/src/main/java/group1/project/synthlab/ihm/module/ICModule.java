package group1.project.synthlab.ihm.module;

import group1.project.synthlab.module.IModule;

public interface ICModule extends IModule {
	public IPModule getPresentation();

	public String saveConfiguration();
}
