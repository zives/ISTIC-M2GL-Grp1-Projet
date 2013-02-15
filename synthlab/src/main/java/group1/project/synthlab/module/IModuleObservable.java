package group1.project.synthlab.module;


/**
 * @author Groupe 1
 *	Pour observer un module
 */
public interface IModuleObservable {
	/**
	 * Ajoute un observateur au module
	 * @param observer
	 */
	public void register(IModuleObserver observer);
	
	
	/**
	 * Se desinscrit aupres du module
	 * @param observer
	 */
	public void unregister(IModuleObserver observer);

	/**
	 * Previens les observateurs que le module est en marche
	 */
	public void warnModuleOn();

	/**
	 * Previens les observateurs que le module est a l'arret
	 */
	public void warnModuleOff();
}
