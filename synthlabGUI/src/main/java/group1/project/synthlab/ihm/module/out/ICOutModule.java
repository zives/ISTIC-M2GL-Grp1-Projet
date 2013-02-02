package group1.project.synthlab.ihm.module.out;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.out.IOutModule;

public interface ICOutModule extends ICModule, IOutModule {
	public IPOutModule getPresentation();
}
