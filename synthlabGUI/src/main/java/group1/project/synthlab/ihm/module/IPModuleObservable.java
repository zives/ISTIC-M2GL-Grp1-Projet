package group1.project.synthlab.ihm.module;

public interface IPModuleObservable {
	public void register(IPModuleObserver observer);
	public void unregister(IPModuleObserver observer);
	public void updateAllMove();
}
