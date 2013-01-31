package group1.project.synthlab.cable;

import group1.project.synthlab.control.port.in.InPort;
import group1.project.synthlab.control.port.out.OutPort;
import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.exceptions.PortAlreadyUsed;

public interface ICable {

	public InPort getInPort();
	public void setInPort(InPort inPort) throws BadConnection, PortAlreadyUsed;
	public OutPort getOutPort();
	public void setOutPort(OutPort outPort) throws BadConnection, PortAlreadyUsed;
	
}
