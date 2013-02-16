package group1.project.synthlab.ihm.module;

/**
 * @author Groupe 1
 * Un observateur de la presentation du module
 */
public interface IPModuleObserver {
	/**
	 * Evenement leve quand la presentation du module s'est deplacee
	 * @param subject la presentation du module
	 */
	public void moduleMove(IPModuleObservable subject); 
}
