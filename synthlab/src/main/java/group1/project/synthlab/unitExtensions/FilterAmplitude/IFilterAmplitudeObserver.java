package group1.project.synthlab.unitExtensions.FilterAmplitude;

public interface IFilterAmplitudeObserver {
	public void warn(IFilterAmplitudeObservable subject, boolean tooHigh);
	public void hasSignal(IFilterAmplitudeObservable subject, boolean hasSignal);
}
