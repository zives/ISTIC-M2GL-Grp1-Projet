package group1.project.synthlab.ihm.module;

public interface PModuleObservable {
	public void register(PModuleObserver observer);
	public void unregister(PModuleObserver observer);
	public void updateAll();
}
