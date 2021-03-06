package group1.project.synthlab.ihm.cable;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.ihm.module.IPModuleObserver;

/**
 * @author Groupe 1
 * Interface de controleur de cable
 */
public interface ICCable extends ICable, IPModuleObserver{
	/**
	 * @return obtient la presentation
	 */
	public IPCable getPresentation();


}
