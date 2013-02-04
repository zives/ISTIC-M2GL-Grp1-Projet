package group1.project.synthlab.ihm.cable;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.ihm.module.IPModuleObserver;

public interface ICCable extends ICable, IPModuleObserver{
	public IPCable getPresentation();

}
