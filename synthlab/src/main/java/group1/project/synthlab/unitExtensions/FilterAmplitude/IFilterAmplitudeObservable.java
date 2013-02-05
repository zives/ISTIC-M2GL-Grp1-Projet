package group1.project.synthlab.unitExtensions.FilterAmplitude;

/**
 * @author Groupe 1
 * Pour observer le filtre d'amplitude
 */
public interface IFilterAmplitudeObservable {
	public void register(IFilterAmplitudeObserver observer);
	public void unregister(IFilterAmplitudeObserver observer);
	public void updateAll(boolean tooHigh);
}
