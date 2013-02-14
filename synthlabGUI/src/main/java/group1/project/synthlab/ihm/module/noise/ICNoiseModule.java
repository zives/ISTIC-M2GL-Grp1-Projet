package group1.project.synthlab.ihm.module.noise;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.noise.INoiseModule;

public interface ICNoiseModule extends ICModule, INoiseModule {
	public IPNoiseModule getPresentation();
}
