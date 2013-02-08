package group1.project.synthlab.ihm.module.eg;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.eg.IEGModule;

public interface ICEGModule extends ICModule, IEGModule {
	public IPEGModule getPresentation();
}
