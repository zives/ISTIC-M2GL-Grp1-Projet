package group1.project.synthlab.ihm.cable;

import group1.project.synthlab.cable.Cable;
import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.ihm.port.in.CInPort;
import group1.project.synthlab.ihm.port.out.COutPort;
import group1.project.synthlab.port.in.InPort;
import group1.project.synthlab.port.out.OutPort;

public class CCable extends Cable implements ICCable{
	protected IPCable presentation;
	public CCable() {
		presentation = new PCable(this);
	}
	@Override
	public void setOutPort(OutPort outPort) throws BadConnection,
			PortAlreadyUsed {
		super.setOutPort(outPort);
		presentation.setP1(((COutPort)outPort).getPresentation());
		presentation.setP2(((COutPort)outPort).getPresentation());
	}
	@Override
	public void setInPort(InPort inPort) throws BadConnection, PortAlreadyUsed {
		presentation.setP2(((CInPort)inPort).getPresentation()); //TODO passer ca en dessous
		super.setInPort(inPort);
		
	}
	public IPCable getPresentation() {
		return presentation;
	}
	
	
}
