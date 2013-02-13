package group1.project.synthlab.ihm.module;

import java.io.Serializable;

public interface IPModule extends IPModuleObservable, Serializable {

	public void start();
	public void stop();
}
