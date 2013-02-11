package group1.project.synthlab.unitExtensions.filterSupervisor;

/**
 * @author Groupe 1
 */
public interface IFilterObservable {
	public void register(IFilterObserver observer);
	public void unregister(IFilterObserver observer);
	public void notifyAllObservers();
}
