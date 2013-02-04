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

	public IInPort getInPort();
	public void setInPort(IInPort inPort) throws BadConnection, PortAlreadyUsed;
	public IOutPort getOutPort();
	public void setOutPort(IOutPort outPort) throws BadConnection, PortAlreadyUsed;
	/**
	 * Suand les deux bouts du cables sont connectés à un port
	 */
	public boolean isConnected();
	public void disconnect();
}
