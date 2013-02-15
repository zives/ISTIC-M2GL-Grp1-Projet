package group1.project.synthlab.port;
/**
 * @author Groupe 1
 * Pour rendre le port observable
 */
public interface IPortObservable {
	/**
	 * Enregistre un observateur apres du port
	 * @param observer
	 */
	public void register(IPortObserver observer);
	
	/**
	 * Desinscrit un observateur au niveau du port
	 * @param observer
	 */
	public void unregister(IPortObserver observer);
	
	/**
	 * Evenement cable connecte
	 */
	public void cableConnected();
	/**
	 * Evenement cable deconnecte
	 */
	public void cableDisconnected();
}
