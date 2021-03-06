package group1.project.synthlab.ihm.port;

import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.port.IPort;

/**
 * @author Groupe 1
 * Interface generale pour un controleur de port
 */
public interface ICPort extends IPort {
	
	
	/**
	 * @return la presentation du port
	 */
	public IPPort getPresentation();

	/**
	 * Essai de generer un cable
	 * @throws BadConnection
	 */
	public void actionCable() throws BadConnection;

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
