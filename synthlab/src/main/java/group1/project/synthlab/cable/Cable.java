package group1.project.synthlab.cable;

import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.port.in.InPort;
import group1.project.synthlab.port.out.OutPort;

public class Cable implements ICable {
	
	protected InPort inPort; // Entrée d'un module
	protected OutPort outPort; // Sortie d'un module
	
	public InPort getInPort() {
		return inPort;
	}


	public void setInPort(InPort inPort) throws BadConnection {
		this.inPort = inPort;
		if(outPort != null){
			outPort.getJSynPort().connect(inPort.getJSynPort());
			outPort.setCable(this);
			inPort.setCable(this);
		}
		else
			throw new BadConnection("Un câble doit partir d'une sortie et arriver à une entrée.");
	}


	public OutPort getOutPort() {
		return outPort;
	}


	public void setOutPort(OutPort outPort) {
		this.outPort = outPort;
		
	}
	
	
	public Cable() throws PortAlreadyUsed, BadConnection {
	}
	
	
	@Override
	public void finalize() throws Throwable{
		this.outPort.getJSynPort().disconnect(inPort.getJSynPort());
		outPort.setCable(null);
		inPort.setCable(null);
		super.finalize();		
	}
}
