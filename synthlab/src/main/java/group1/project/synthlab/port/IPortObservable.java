package group1.project.synthlab.port;

/**
 * @author Groupe 1
 * 
 */
public interface IPortObservable {
	public void register(IPortObserver observer);
	public void unregister(IPortObserver observer);
	/**
	 * evenement cable connecte
	 */
	public void cableConnected();
	/**
	 * evenement cable deconnecte
	 */
	public void cableDisconnected();
}
