package group1.project.synthlab.unitExtensions.filterSupervisor;

/**
 * @author Groupe 1
 * Pour observer le filtre d'amplitude
 */
public interface IFilterAmplitudeObservable {
	public void register(IFilterAmplitudeObserver observer);
	public void unregister(IFilterAmplitudeObserver observer);
	public void updateWarnAll(boolean tooHigh);
	public void updateHasSignalAll(boolean tooHigh);
}
