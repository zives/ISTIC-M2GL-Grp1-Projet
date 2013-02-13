package group1.project.synthlab.unitExtension.filter.filterInterception;

import group1.project.synthlab.unitExtension.filter.filterSupervisor.IFilterAmplitudeObserver;

public interface IFilterInterceptionObservable {
	/**
	 * @param observer l'observeur
	 */
	public void register(IFilterInterceptionObserver observer);
	
	/**
	 * annule l'observation
	 * @param observer le sujet
	 */
	public void unregister(IFilterAmplitudeObserver observer);

}
