package group1.project.synthlab.ihm.port;

/**
 * @author Groupe 1
 * Interface de presentation d'un port
 */
public interface IPPort {
	/**
	 * Indique que le port n'est pas selectionnable
	 */
	public void setForbidden();
	/**
	 * Indique que le port est selectionnable
	 */
	public void setAllowed();
	
	/**
	 * Indique que le port peut supprimer le cable
	 */
	public void setAllowedToDelete();
	
	/**
	 * Retourne le controler
	 */
	public ICPort getController();
}
