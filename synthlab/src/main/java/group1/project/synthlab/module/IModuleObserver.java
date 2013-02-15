package group1.project.synthlab.module;


public interface IModuleObserver {

	public void moduleIsOff(IModuleObservable module);

	public void moduleIsOn(IModuleObservable module);
}
