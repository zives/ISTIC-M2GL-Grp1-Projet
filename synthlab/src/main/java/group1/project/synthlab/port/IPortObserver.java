package group1.project.synthlab.port;

/**
 * @author Groupe 1
 * Interface pour observer un port
 */
public interface IPortObserver {
	/**
	 * Evenement leve quand un cable est connecte
	 * @param port le port sur lequel le cable a ete connecte
	 */
	public void cableConnected(IPort port);
	/**
	 * Evenement leve quand un cable est deconnecte
	 * @param port le port sur lequel le cable a ete deconnecte
	 */
	public void cableDisconnected(IPort port);
}
