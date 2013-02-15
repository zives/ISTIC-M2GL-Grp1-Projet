package group1.project.synthlab.module;

import group1.project.synthlab.port.IPortObserver;

public interface IModuleObservable {
	public void register(IModuleObserver observer);
	public void unregister(IModuleObserver observer);

	public void warnModuleOn();

	public void warnModuleOff();
}
