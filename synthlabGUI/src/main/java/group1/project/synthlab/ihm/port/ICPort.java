package group1.project.synthlab.ihm.port;

import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.port.IPort;

public interface ICPort extends IPort {
	public IPPort getPresentation();

	/**
	 * Essai de generer un cable
	 * @throws BadConnection
	 */
	public void actionCable()  throws BadConnection;

	/**
	 * Previens la presentation si la pose d'un cable est possible
	 */
	public void checkPutCable();

	/**
	 * Supprime un cable lie a ce port
	 * @throws BadConnection
	 */
	public void removeCable()  throws BadConnection;
}
