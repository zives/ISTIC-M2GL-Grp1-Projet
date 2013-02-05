package group1.project.synthlab.ihm.port;

public interface IPPort {
	/**
	 * Indique que le port n'est pas selectionnable
	 */
	public void setForbidden();
	/**
	 * Indique que le port est selectionnable
	 */
	public void setAllowed();
}
