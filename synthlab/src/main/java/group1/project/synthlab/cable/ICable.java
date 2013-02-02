package group1.project.synthlab.cable;

import group1.project.synthlab.exceptions.BadConnection;

/**
 * Interface de création d'un cable
 * 
 * @author Groupe 1
 * 
 */
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.in.InPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.port.out.OutPort;

public interface ICable {

	public IInPort getInPort();
	public void setInPort(IInPort inPort) throws BadConnection, PortAlreadyUsed;
	public IOutPort getOutPort();
	public void setOutPort(IOutPort outPort) throws BadConnection, PortAlreadyUsed;
	public void disconnect();
}
