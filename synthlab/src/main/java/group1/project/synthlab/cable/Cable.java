package group1.project.synthlab.cable;

import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;

/**
 * Création d'un cable
 * 
 * @author Groupe 1
 * 
 */

public class Cable implements ICable {
	
	protected IInPort inPort; // Entree d'un module
	protected IOutPort outPort; // Sortie d'un module
	protected Factory factory;
	
	/**
	 * Constructeur du cable.
	 * @param factory
	 */
	public Cable(Factory factory) {
		this.factory = factory;
	}

	/**
	 * Mise a jour de la connection du port de sortie.
	 * Lorsqu'on cree un cable, on commence par cliquer sur la sortie d'un module.
	 */
	public void setOutPort(IOutPort outPort) throws BadConnection, PortAlreadyUsed {
		if (outPort.isUsed())
			throw new PortAlreadyUsed("Ce port " + outPort.getLabel() + " est deja utilise par un autre cable. Dettachez le cable avant d'en ajouter un autre !");
		this.outPort = outPort;
		
	}
	
	/**
	 * Mise a jour de la connection du port d'entree.
	 * A la creation d'un cable, apres avoir clique sur la sortie d'un module,
	 * on termine en cliquant sur l'entree d'un autre module.
	 */
	public void setInPort(IInPort inPort) throws BadConnection, PortAlreadyUsed {
		if (inPort.isUsed())
			throw new PortAlreadyUsed("Ce port " + inPort.getLabel() + " est deja utilise par un autre cable. Dettachez le cable avant d'en ajouter un autre !");
		this.inPort = inPort;
		if(outPort != null){
			outPort.getJSynPort().connect(inPort.getJSynPort());
			outPort.setCable(this);
			inPort.setCable(this);
			// On previent les observers qu'un cable connecte maintenant les 2 ports.
			outPort.cableConnected();
			inPort.cableConnected();
		}
		else
			throw new BadConnection("Un cable doit partir d'une sortie de module et arriver a une entree d'un autre module.");
	}
	
	/**
	 * Retourne le port d'entree.
	 */
	public IInPort getInPort() {
		return inPort;
	}
	
	/**
	 * Retourne le port de sortie.
	 */
	public IOutPort getOutPort() {
		return outPort;
	}

	/**
	 * Deconnecte les 2 ports.
	 */
	public void disconnect() {
		this.outPort.getJSynPort().disconnect(inPort.getJSynPort());
		outPort.setCable(null);
		inPort.setCable(null);
		// On previent les observers que dorénavant aucun cable ne connecte les 2 ports.
		outPort.cableDisconnected();
		inPort.cableDisconnected();
	}
	
	@Override
	public void finalize() throws Throwable{
		disconnect();
		super.finalize();		
	}
}
