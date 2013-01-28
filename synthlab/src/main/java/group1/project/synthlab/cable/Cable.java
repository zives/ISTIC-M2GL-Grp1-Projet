package group1.project.synthlab.cable;

import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.port.IPort.PortType;
import group1.project.synthlab.port.Port;

public class Cable implements ICable {
	
	public Cable(Port in, Port out) throws BadConnection {
		if (in.getType() != PortType.IN)
			throw new BadConnection("In port argument is not of type IN");
		if (out.getType() != PortType.OUT)
			throw new BadConnection("out port argument is not of type OUT");
		out.connectTo(in);		
	}
}
