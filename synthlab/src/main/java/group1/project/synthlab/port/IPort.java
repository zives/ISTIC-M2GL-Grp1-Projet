package group1.project.synthlab.port;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.module.IModule;

public interface IPort extends IPortObservable {
	public String getLabel();
	public ICable getCable();
	public void setCable(ICable cable);
	public boolean isUsed();
	/**
	 * @return le module rattaché
	 */
	public IModule getModule();
}
