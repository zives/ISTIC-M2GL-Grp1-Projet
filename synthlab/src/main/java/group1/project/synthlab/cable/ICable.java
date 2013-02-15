package group1.project.synthlab.cable;

import java.io.Serializable;

import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Signal;
/**
 * Interface de creation d'un cable
 * 
 * @author Groupe 1
 * 
 */

public interface ICable extends Serializable {

	/**
	 * @return le port d'entree du module connecte
	 */
	public IInPort getInPort();
	
	/**
	 * Mise a jour de la connection du port d'entree.
	 * A la creation d'un cable, apres avoir clique sur la sortie d'un module,
	 * on termine en cliquant sur l'entree d'un autre module.
	 * @param inPort me port d'entree
	 * @throws BadConnection
	 * @throws PortAlreadyUsed
	 */
	public void setInPort(IInPort inPort) throws BadConnection, PortAlreadyUsed;
	
	/**
	 * @return le port de sortie du module connecte
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
	 * @return si le cable est connecte a deux ports (n'est pas en etat de creation)
	 */
	public boolean isConnected();
	
	
	/**
	 * detruit les connexions du cable
	 */
	public void disconnect();
	
	
	/**
	 * @return si il y a un signal qui circule
	 */
	public boolean hasSignal();
	
	/**
	 * @return si le signal est sature (> AMAX @see {@link Signal})
	 */
	public boolean isSignalSaturated();
	
	
	/**
	 * @return le numero du cable (incremente a chaque creation de cable)
	 */
	public int getNumCable();

	/**
	 * @param numCable donne un numero au cable (force le numero, non recommande)
	 */
	public void setNumCable(int numCable);
	
	

}
