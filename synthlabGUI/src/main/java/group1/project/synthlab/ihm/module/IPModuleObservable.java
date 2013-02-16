package group1.project.synthlab.ihm.module;

/**
 * @author Groupe 1
 * Rend la presentation d'un module observable
 */
public interface IPModuleObservable {
	
	/**
	 * Enregistre un observateur a la presentation
	 * @param observer
	 */
	public void register(IPModuleObserver observer);
	
	
	/**
	 * Annule l'observation d'un observateur
	 * @param observer
	 */
	public void unregister(IPModuleObserver observer);
	
	
	/**
	 * Indique aux observateurs que la presentation s'est deplacee
	 */
	public void updateAllMove();
}
