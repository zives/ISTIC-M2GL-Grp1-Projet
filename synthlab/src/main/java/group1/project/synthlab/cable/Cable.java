package group1.project.synthlab.cable;

import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.port.in.InPort;
import group1.project.synthlab.port.out.OutPort;

public class Cable implements ICable {
	
	protected InPort inPort; // Entrée d'un module
	protected OutPort outPort; // Sortie d'un module
	
	public Cable() {
	}

	// Lorsqu'on crée un cable, on commence par cliquer sur la sortie d'un module...
	public void setOutPort(OutPort outPort) throws BadConnection, PortAlreadyUsed {
		if (outPort.isUsed())
			throw new PortAlreadyUsed("Ce port " + outPort.getLabel() + " est déjà utilisé par un autre cable. Déttacher le câble avant d'en ajouter un autre!");
		this.outPort = outPort;
		
	}
	
	// Et on termine en cliquant sur l'entrée d'un module
	public void setInPort(InPort inPort) throws BadConnection, PortAlreadyUsed {
		if (inPort.isUsed())
			throw new PortAlreadyUsed("Ce port " + inPort.getLabel() + " est déjà utilisé par un autre cable. Déttacher le câble avant d'en ajouter un autre!");
		this.inPort = inPort;
		if(outPort != null){
			outPort.getJSynPort().connect(inPort.getJSynPort());
			outPort.setCable(this);
			inPort.setCable(this);
			// On prévient les observers qu'un câble connecte maintenant les 2 ports
			outPort.cableConnected();
			inPort.cableConnected();
		}
		else
			throw new BadConnection("Un câble doit partir d'une sortie et arriver à une entrée.");
	}

	public InPort getInPort() {
		return inPort;
	}
	
	public OutPort getOutPort() {
		return outPort;
	}
	
	@Override
	public void finalize() throws Throwable{
		this.outPort.getJSynPort().disconnect(inPort.getJSynPort());
		outPort.setCable(null);
		inPort.setCable(null);
		// On prévient les observers qu'aucun câble ne connecte les 2 ports à présent
		outPort.cableDisconnected();
		inPort.cableDisconnected();
		super.finalize();		
	}
}
