package group1.project.synthlab.cable;

import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
/**
 * Interface de creation d'un cable
 * 
 * @author Groupe 1
 * 
 */

public interface ICable {

	/**
	 * @return le port d'entrée du module connecté
	 */
	public IInPort getInPort();
	
	/**
	 * Mise a jour de la connection du port d'entree.
	 * A la creation d'un cable, apres avoir clique sur la sortie d'un module,
	 * on termine en cliquant sur l'entree d'un autre module.
	 * @param inPort
	 * @throws BadConnection
	 * @throws PortAlreadyUsed
	 */
	public void setInPort(IInPort inPort) throws BadConnection, PortAlreadyUsed;
	
	/**
	 * @return le port de sortie du module connecté
	 */
	public IOutPort getOutPort();
	
	/**
	 * Mise a jour de la connexion du port de sortie.
	 * Lorsqu'on cree un cable, on commence par cliquer sur la sortie d'un module.
	 * @param outPort le port de sortie 
	 * @throws BadConnection
	 * @throws PortAlreadyUsed
	 */
	public void setOutPort(IOutPort outPort) throws BadConnection, PortAlreadyUsed;
	
	/**
	 * @return si le cable est connecte a deux ports (n'est pas en etat de dessin)
	 */
	public boolean isConnected();
	
	
	/**
	 * detruit les connexions du cable
	 */
	public void disconnect();
	
	
	/** Défini sir l'amplitude du signal est trop elevée */
	public void setSignalSaturated(boolean saturated);
	
	/**
	 * @return si le signal est saturé
	 */
	public boolean isSignalSaturated();
}
