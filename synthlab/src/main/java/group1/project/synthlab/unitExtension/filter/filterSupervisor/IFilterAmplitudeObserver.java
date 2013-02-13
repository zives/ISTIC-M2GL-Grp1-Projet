package group1.project.synthlab.unitExtension.filter.filterSupervisor;

public interface IFilterAmplitudeObserver {
	public void warn(IFilterAmplitudeObservable subject, boolean tooHigh);
	public void hasSignal(IFilterAmplitudeObservable subject, boolean hasSignal);
}
