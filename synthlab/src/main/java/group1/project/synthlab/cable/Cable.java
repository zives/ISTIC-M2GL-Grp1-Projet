package group1.project.synthlab.cable;

import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.port.in.InPort;
import group1.project.synthlab.port.out.OutPort;

public class Cable implements ICable {
	protected InPort inPort;
	protected OutPort outPort;
	
	public Cable(InPort in, OutPort out) throws PortAlreadyUsed, BadConnection {
		this.outPort = out;
		this.inPort = in;
		out.connectTo(in);	
		in.connectTo(out); //TODO : vérifier que celà ne pose pas de problèmes
	}
	
	@Override
	public void finalize() throws Throwable{
		this.outPort.disconnect();
		this.inPort.disconnect();
		super.finalize();		
	}
}
