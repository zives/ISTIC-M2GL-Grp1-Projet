package group1.project.synthlab.unitExtension.filter.filterSupervisor;

/**
 * @author Groupe 1
 * Observateur du filtre amplitude
 */
public interface IFilterAmplitudeObserver {
	/**
	 * Evenement leve lorsque le signal est sature ou ne l'est plus
	 * @param subject le filtre
	 * @param tooHigh si le signal est sature
	 */
	public void warnSignalIsSatured(IFilterAmplitudeObservable subject, boolean tooHigh);
		
	/**
	 * Evenement leve lorsqu'un circule ou non
	 * @param subject le filtre
	 * @param hasSignal si le signal circule
	 */
	public void hasSignal(IFilterAmplitudeObservable subject, boolean hasSignal);
}
