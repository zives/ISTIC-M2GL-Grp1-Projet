package group1.project.synthlab.module;


/**
 * @author Groupe 1
 * Un observateur d'un module
 */
public interface IModuleObserver {

	/**
	 * Evenement quand un module est arrete
	 * @param module
	 */
	public void moduleIsOff(IModuleObservable module);

	/**
	 * Evenement quand un module est demarre
	 * @param module
	 */
	public void moduleIsOn(IModuleObservable module);
}
