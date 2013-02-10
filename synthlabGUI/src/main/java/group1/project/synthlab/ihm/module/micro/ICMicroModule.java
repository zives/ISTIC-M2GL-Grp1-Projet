package group1.project.synthlab.ihm.module.micro;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.micro.IMicroModule;

public interface ICMicroModule extends ICModule, IMicroModule {
	public IPMicroModule getPresentation();
}
