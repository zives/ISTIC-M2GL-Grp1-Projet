package group1.project.synthlab.unitExtension.filter.filterSupervisor;

/**
 * @author Groupe 1
 * Pour observer le filtre d'amplitude
 */
public interface IFilterAmplitudeObservable {
	
	/**
	 * Enregistre un observateur apres du filtre
	 * @param observer 
	 */
	public void register(IFilterAmplitudeObserver observer);
	
	
	/**
	 * Annule l'observation pour un observateur
	 * @param observer
	 */
	public void unregister(IFilterAmplitudeObserver observer);
	
	
	/**
	 * Previens les observateurs d'un signal sature ou s'il ne l'est plus
	 * @param tooHigh si le signal est sature
	 */
	public void updateWarnAllSignalIsSaturated(boolean tooHigh);
	
	
	/**
	 * Previens les observateurs d'un signal circulant
	 * @param hasSignal si il y a un signal ou s'il n'y en a plus
	 * Note : cette methode n'est normalement appele que lors d'un changement d'etat et pas 
	 * tout le temps
	 */
	public void updateHasSignalAll(boolean hasSignal);
}
