package group1.project.synthlab.cable;

import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.port.in.InPort;
import group1.project.synthlab.port.out.OutPort;

public interface ICable {

	public InPort getInPort();
	public void setInPort(InPort inPort) throws BadConnection, PortAlreadyUsed;
	public OutPort getOutPort();
	public void setOutPort(OutPort outPort);
	
}
