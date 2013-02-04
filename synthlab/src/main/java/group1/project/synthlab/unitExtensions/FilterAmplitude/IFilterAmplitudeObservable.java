package group1.project.synthlab.unitExtensions.FilterAmplitude;

public interface IFilterAmplitudeObservable {
	public void register(IFilterAmplitudeObserver observer);
	public void unregister(IFilterAmplitudeObserver observer);
	public void updateAll(boolean tooHigh);
}
