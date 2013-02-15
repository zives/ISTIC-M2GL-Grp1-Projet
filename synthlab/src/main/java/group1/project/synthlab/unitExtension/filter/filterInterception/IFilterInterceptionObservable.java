package group1.project.synthlab.unitExtension.filter.filterInterception;

import group1.project.synthlab.unitExtension.filter.filterSupervisor.IFilterAmplitudeObserver;

/**
 * @author Groupe 1
 * Rend le filtre interception observable
 */
public interface IFilterInterceptionObservable {
	/**
	 * Enregistre un observateur aupres du filtre
	 * @param observer l'observateur
	 */
	public void register(IFilterInterceptionObserver observer);
	
	/**
	 * Annule l'observation
	 * @param observer l'onservateur
	 */
	public void unregister(IFilterAmplitudeObserver observer);

}
